package com.ire.app.service.impl;

import Jama.Matrix;
import com.ire.app.model.AlgorithmConfigData;
import com.ire.app.model.DataForAlgorithm;
import com.ire.app.model.DataToConvert;
import com.ire.app.model.entity.AlgorithmsConfig;
import com.ire.app.model.entity.ConvertedDataInfo;
import com.ire.app.model.entity.ImportedRow;
import com.ire.app.model.entity.RowAttribute;
import com.ire.app.repository.AlgorithmConfigurationRepository;
import com.ire.app.service.AlgorithmsService;
import com.jujutsu.tsne.barneshut.BHTSne;
import com.jujutsu.tsne.barneshut.BarnesHutTSne;
import com.jujutsu.tsne.barneshut.TSneConfig;
import com.jujutsu.tsne.barneshut.TSneConfiguration;
import com.mkobos.pca_transform.PCA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smile.manifold.IsoMap;
import smile.manifold.LLE;
import smile.mds.MDS;
import smile.mds.SammonMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AlgorithmsServiceImpl implements AlgorithmsService {

    private final BarnesHutTSne barnesHutTSne = new BHTSne();

    @Autowired
    private AlgorithmConfigurationRepository algorithmConfigurationRepository;

    @Override
    public double[][] usePcaAlgorithm(double[][] dataMatrix) {
        Matrix algorithmData = new Matrix(dataMatrix);

        PCA pca = new PCA(algorithmData);
        Matrix transformedData =
                pca.transform(algorithmData, PCA.TransformationType.WHITENING);
        return transformedData.getArray();
    }

    @Override
    public double[][] useTSneAlgorithm(double[][] dataMatrix) {
        TSneConfiguration tSneConfiguration = this.prepareTSneConfiguration(dataMatrix);
        double[][] tsneResults = barnesHutTSne.tsne(tSneConfiguration);
        return tsneResults;
    }

    @Override
    public double[][] useLLEAlgorithm(double[][] dataMatrix) {
        AlgorithmsConfig configuration = getLLENeighboursConfiguration();
        LLE lle = new LLE(dataMatrix, 2, configuration.getNeighboursNo());
        double[][] coordinates = lle.getCoordinates();
        return coordinates;
    }

    @Override
    public DataForAlgorithm prepareDataForAlgorithm(DataToConvert dataToConvert) {
        List<ImportedRow> importedRows = dataToConvert.getImportedRowList();
        List<RowAttribute> attributes = dataToConvert.getRowAttributes();
        double[][] valuesForAlgorithm = new double[importedRows.size()][];
        List<String> diagnoses = new ArrayList<>();
        List<Integer> originalDataRowIds = new ArrayList<>();
        for(int i = 0; i < importedRows.size(); i++){
            double[] attrForRow = getAttributesForRow(importedRows.get(i).getRowId(), attributes);
            valuesForAlgorithm[i] = attrForRow;
            diagnoses.add(importedRows.get(i).getDiagnosis().toString());
            originalDataRowIds.add(importedRows.get(i).getRowId());
        }
        DataForAlgorithm dataForAlgorithm = new DataForAlgorithm();
        dataForAlgorithm.setData(valuesForAlgorithm);
        dataForAlgorithm.setDiagnoses(diagnoses);
        dataForAlgorithm.setOriginalDataRowIds(originalDataRowIds);
        return dataForAlgorithm;
    }

    @Override
    public List<AlgorithmConfigData> getActualAlgorithmsConfiguration() {
        AlgorithmsConfig algorithmsConfig = algorithmConfigurationRepository.findTopByAlgorithmNameOrderByIdDesc(
                ConvertedDataInfo.ALGORITHM.TSNE.getAlgorithmName());
        List<AlgorithmConfigData> algorithmConfigList = new ArrayList<>();
        AlgorithmConfigData algorithmConfigData = new AlgorithmConfigData(
                algorithmsConfig.getAlgorithmName().getAlgorithmName(),
                algorithmsConfig.getMaxIteration(),
                algorithmsConfig.getPerplexity(),
                algorithmsConfig.getTheta(),
                algorithmsConfig.getUsePca()
        );
        algorithmConfigList.add(algorithmConfigData);
        return algorithmConfigList;
    }

    @Override
    public void saveAlgorithmConfiguration(AlgorithmConfigData algorithmConfigData) {
        AlgorithmsConfig algorithmsConfig = new AlgorithmsConfig();
        ConvertedDataInfo.ALGORITHM algorithm;
        if(ConvertedDataInfo.ALGORITHM.TSNE.getAlgorithmName().equals(algorithmConfigData.getAlgorithmName())){
            algorithm = ConvertedDataInfo.ALGORITHM.TSNE;
        }else if(ConvertedDataInfo.ALGORITHM.PCA.getAlgorithmName().equals(algorithmConfigData.getAlgorithmName())){
            algorithm = ConvertedDataInfo.ALGORITHM.PCA;
        }else {
            algorithm = ConvertedDataInfo.ALGORITHM.UNKNOWN;
        }
        algorithmsConfig.setAlgorithmName(algorithm);
        algorithmsConfig.setMaxIteration(algorithmConfigData.getMaxIteration());
        algorithmsConfig.setPerplexity(algorithmConfigData.getPerplexity());
        algorithmsConfig.setTheta(algorithmConfigData.getTheta());
        algorithmsConfig.setUsePca(algorithmConfigData.getUsePCA());
        AlgorithmsConfig save = algorithmConfigurationRepository.save(algorithmsConfig);
        if(save == null){
            throw new IllegalArgumentException("Error on saving algorithm config");
        }
    }

    private double[] getAttributesForRow(int rowID, List<RowAttribute> attributes){
        List<RowAttribute> rowAttributes = attributes.stream().filter(attr -> attr.getCopyRowId() == rowID)
                .collect(Collectors.toList());
        double[] attributeArray = new double[rowAttributes.size()];
        for(int i = 0; i < rowAttributes.size(); i++){
            attributeArray[i] = rowAttributes.get(i).getValue();
        }
        return attributeArray;
    }

    private TSneConfiguration prepareTSneConfiguration(double[][] dataMatrix){
        AlgorithmsConfig algorithmConfig = algorithmConfigurationRepository
                .findTopByAlgorithmNameOrderByIdDesc(ConvertedDataInfo.ALGORITHM.TSNE.getAlgorithmName());
        TSneConfiguration tSneConfiguration = new TSneConfig(
                dataMatrix, 2, dataMatrix.length, algorithmConfig.getPerplexity(),
                algorithmConfig.getMaxIteration(), algorithmConfig.getUsePca(), algorithmConfig.getTheta(), false, true);
        return tSneConfiguration;
    }

    private AlgorithmsConfig getLLENeighboursConfiguration(){
        return algorithmConfigurationRepository
                .findTopByAlgorithmNameOrderByIdDesc(ConvertedDataInfo.ALGORITHM.LLE.getAlgorithmName());
    }
}
