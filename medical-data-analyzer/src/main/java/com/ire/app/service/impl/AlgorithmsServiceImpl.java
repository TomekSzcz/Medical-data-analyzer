package com.ire.app.service.impl;

import com.ire.app.model.DataForAlgorithm;
import com.ire.app.model.DataToConvert;
import com.ire.app.model.entity.ImportedRow;
import com.ire.app.model.entity.RowAttribute;
import com.ire.app.service.AlgorithmsService;
import com.jujutsu.tsne.barneshut.BHTSne;
import com.jujutsu.tsne.barneshut.BarnesHutTSne;
import com.jujutsu.tsne.barneshut.TSneConfig;
import com.jujutsu.tsne.barneshut.TSneConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AlgorithmsServiceImpl implements AlgorithmsService {


    private final BarnesHutTSne barnesHutTSne = new BHTSne();

    @Override
    public double[][] usePcaAlgorithm(double[][] dataMatrix) {

        return new double[0][];
    }

    @Override
    public double[][] useTSneAlgorithm(double[][] dataMatrix) {
        TSneConfiguration tSneConfiguration = this.prepareTSneConfiguration(dataMatrix);
        double[][] tsneResults = barnesHutTSne.tsne(tSneConfiguration);
        return tsneResults;
    }

    @Override
    public DataForAlgorithm prepareDataForAlgorithm(DataToConvert dataToConvert) {
        List<ImportedRow> importedRows = dataToConvert.getImportedRowList();
        List<RowAttribute> attributes = dataToConvert.getRowAttributes();
        double[][] valuesForAlgorithm = new double[importedRows.size()][];
        List<String> diagnoses = new ArrayList<>();
        for(int i = 0; i < importedRows.size(); i++){
            double[] attrForRow = getAttributesForRow(importedRows.get(i).getRowId(), attributes);
            valuesForAlgorithm[i] = attrForRow;
            diagnoses.add(importedRows.get(i).getDiagnosis().toString());
        }
        DataForAlgorithm dataForAlgorithm = new DataForAlgorithm();
        dataForAlgorithm.setData(valuesForAlgorithm);
        dataForAlgorithm.setDiagnoses(diagnoses);
        return dataForAlgorithm;
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
        TSneConfiguration tSneConfiguration = new TSneConfig(
                dataMatrix, 2, dataMatrix.length, 20.0,
                3000, true, 5, false, true);
        return tSneConfiguration;

    }
}
