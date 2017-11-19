package com.ire.app.service.impl;

import com.ire.app.model.*;
import com.ire.app.model.entity.*;
import com.ire.app.repository.*;
import com.ire.app.service.CSVReaderService;
import com.ire.app.service.ManageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManageDataServiceImpl implements ManageDataService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ManageDataService.class);

    @Autowired
    private CSVReaderService csvReaderService;

    @Autowired
    private ImportedDataRepository importedDataRepository;

    @Autowired
    private ImportedRowRepository importedRowRepository;

    @Autowired
    private RowAttributesRepository rowAttributesRepository;

    @Autowired
    private ConvertedDataInfoRepository convertedDataInfoRepository;

    @Autowired
    private ConvertedDataRowsRepository convertedDataRowsRepository;


    @Override
    public boolean importData(MultipartFile multipartFile) {
        try {
            File file = transferMultipartToConvFile(multipartFile);
            List<List<String>> dataToImport = csvReaderService.readCSVfile(file);
            collectAndSaveData(dataToImport, file.getName());

        } catch (Exception e) {
            LOGGER.error("Cannot import data {}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void saveProcessedData(ConvertedDataInfo convertedDataInfo,
                                  double[][] dataMatrix, List<String> diagnoses) {
        try{
            ConvertedDataInfo dataInfo = convertedDataInfoRepository.save(convertedDataInfo);
            int importId = dataInfo.getImportId();
            ConvertedDataRowsPK convertedDataRowsPK = new ConvertedDataRowsPK();

            for(int i = 0; i < diagnoses.size(); i++){
                convertedDataRowsPK.setId(i);
                convertedDataRowsPK.setImportId(importId);
                ConvertedDataRows convertedDataRows = new ConvertedDataRows();
                convertedDataRows.setConvertedDataRowsPK(convertedDataRowsPK);
                convertedDataRows.setDiagnosis(diagnoses.get(i));
                convertedDataRows.setAxisX(dataMatrix[i][0]);
                convertedDataRows.setAxisY(dataMatrix[i][1]);
                convertedDataRowsRepository.save(convertedDataRows);
            }
            markImportProcessed(importId, convertedDataInfo.getAlgorithm());

        }catch (Exception e){
            LOGGER.error("Error occurred during saving processed data {} , reason: {}",
                    convertedDataInfo.getFileName(), e.getMessage());
        }
    }

    @Override
    public List<DataToConvert> collectDataForTSNE() {
        List<DataToConvert> dataToConvertList = new ArrayList<>();
        List<ImportedDataModel> importedDataForTSNE =
                importedDataRepository.getAllByImportStatusAndConvertedByTSNE(
                ImportedDataModel.IMPORT_STATUS.SUCCESS.toString(), false);
        importedDataForTSNE.stream().forEach(impData->{
            DataToConvert dataToConvert = prepareDataToConvert(impData);
            dataToConvertList.add(dataToConvert);
        });
        return dataToConvertList;
    }

    @Override
    public List<DataToConvert> collectDataForPCA() {
        List<DataToConvert> dataToConvertList = new ArrayList<>();
        List<ImportedDataModel> importedDataForPSA =
                importedDataRepository.getAllByImportStatusAndConvertedByPSA(
                ImportedDataModel.IMPORT_STATUS.SUCCESS.toString(), false);
        importedDataForPSA.stream().forEach(impData->{
            DataToConvert dataToConvert = prepareDataToConvert(impData);
            dataToConvertList.add(dataToConvert);
        });
        return dataToConvertList;
    }

    @Override
    public List<AvailableData> getImports() {
        List<ImportedDataModel> importedData = importedDataRepository.getAllByImportStatus(
                ImportedDataModel.IMPORT_STATUS.SUCCESS.toString());
        List<AvailableData> availableDataList = new ArrayList<>();
        for (ImportedDataModel row: importedData) {
            String fileName = row.getFileName();
            int id = row.getId();
            Date importDate = row.getImportDate();
            boolean tsne = row.isConvertedByTSNE();
            boolean pca = row.isConvertedByPSA();
            AvailableData availableData = new AvailableData(fileName, id, tsne, pca, importDate);
            availableDataList.add(availableData);
        }
        return availableDataList;
    }

    @Override
    public List<DataForChart> getChartData(int importId, ConvertedDataInfo.ALGORITHM algorithm) {
        if(importId == 0){
            throw new IllegalArgumentException("Import Id cannot be equals null!");
        }
        List<ConvertedDataRows> convertedDataRows =
                convertedDataRowsRepository.getAllByConvertedDataRowsPK_ImportIdOrderByCopyId(importId);
        List<DataForChart> dataForChartList = new ArrayList<>();
        for (ConvertedDataRows row : convertedDataRows){
            DataForChart dataForChart = new DataForChart(row.getAxisX(),row.getAxisY(),row.getDiagnosis());
            dataForChartList.add(dataForChart);
        }
        return dataForChartList;
    }

    private File transferMultipartToConvFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    private void collectAndSaveData(List<List<String>> dataToImport, String fileName){
        ImportedDataModel importedData = insertMainData(dataToImport.get(0).size(), fileName);
        LOGGER.info("Main data inserted: {}, date with id: {}", fileName, importedData.getId());
        int diagnosisPosition = readDiagnosisPosition(dataToImport.get(0), importedData);
        try{
            importRows(dataToImport, diagnosisPosition, importedData.getId());
            markImportCompleted(importedData);
        }catch (Exception e){
            LOGGER.error("Error during importing rows: {}", e.getMessage());
            markImportFailed(importedData);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private ImportedDataModel insertMainData(int attributeNumber, String fileName){
        ImportedDataModel importedDataModel = new ImportedDataModel();
        ImportedDataModel savedData;
        try {
            importedDataModel.setAttributesNumber(attributeNumber);
            importedDataModel.setImportDate(new java.sql.Date(System.currentTimeMillis()));
            importedDataModel.setFileName(fileName);
            importedDataModel.setImportStatus(ImportedDataModel.IMPORT_STATUS.IN_PROGRESS);
            savedData = importedDataRepository.save(importedDataModel);
        }catch (Exception e){
            LOGGER.error("Cannot import data {} , reason: {}", fileName, e.getMessage());
            throw new IllegalArgumentException("Cannot save data");
        }

        if(savedData == null){
            LOGGER.error("Cannot import data {} , reason: Database problem", fileName);
            throw new IllegalArgumentException("Cannot save data - database is not available");
        }
        return savedData;
    }

    private int readDiagnosisPosition(List<String> firstRow, ImportedDataModel importedDataModel) throws IllegalArgumentException {
        int diagnosisColumnNumber = firstRow.indexOf("diagnosis");
        if(diagnosisColumnNumber < 0){
            LOGGER.warn("There is no diagnosis column! {}", diagnosisColumnNumber);
            markImportFailed(importedDataModel);
            throw new IllegalArgumentException("There is no diagnosis column!");
        }
        return diagnosisColumnNumber;
    }

    private void markImportFailed(ImportedDataModel importedDataModel){
        importedDataModel.setImportStatus(ImportedDataModel.IMPORT_STATUS.FAILED);
        importedDataRepository.save(importedDataModel);
    }

    private void markImportCompleted(ImportedDataModel importedDataModel){
        importedDataModel.setImportStatus(ImportedDataModel.IMPORT_STATUS.SUCCESS);
        importedDataRepository.save(importedDataModel);
    }

    private void importRows(List<List<String>> rows, int diagnosisPosition, int importedDataId){
        List<List<String>> rowsToImport = rows;
        List<String> headers = rows.get(0);
        rowsToImport.stream().skip(1).forEach(
                (row)-> importRowAndAssignedValues(row, headers, diagnosisPosition, importedDataId));

    }

    private void importRowAndAssignedValues(List<String> values, List<String> headers,
                                            int diagnosisPosition, int importedDataId){

        ImportedRow importedRow = importRow(values, diagnosisPosition, importedDataId);
        importRowValues(importedRow.getRowId(), values, headers);

    }

    private ImportedRow importRow(List<String> values, int diagnosisPosition, int importedDataId){
        ImportedRow importedRow = new ImportedRow();
        try {
            String diagnosisValue = values.get(diagnosisPosition);
            importedRow.setDiagnosis(readDiagnosis(Integer.parseInt(diagnosisValue)));
            importedRow.setImportId(importedDataId);
            return importedRowRepository.save(importedRow);
        }catch (Exception e){
            LOGGER.error("Error during importing data with id: {} , reason: {}", importedDataId, e.getMessage());
            throw new IllegalArgumentException("Error during importing one of rows!");
        }
    }

    private ImportedRow.DIAGNOSIS readDiagnosis(int diagnosis) throws IllegalArgumentException{
        if(diagnosis == 0){
            return ImportedRow.DIAGNOSIS.HEALTHY;
        }else if(diagnosis == 1){
            return ImportedRow.DIAGNOSIS.SICK;
        }else {
            throw new IllegalArgumentException("Unknown diagnosis value");
        }
    }

    private void importRowValues(int rowId, List<String> values, List<String> headers){
        try {
            for(int i = 0; i < values.size(); i++){
                RowAttribute rowAttribute = new RowAttribute();
                rowAttribute.setAttributeName(headers.get(i));
                rowAttribute.setValue(Double.parseDouble(values.get(i)));
                RowAttributePK rowAttributePK = new RowAttributePK();
                rowAttributePK.setAttributeId(i);
                rowAttributePK.setRowId(rowId);
                rowAttribute.setRowAttributePK(rowAttributePK);
                rowAttributesRepository.save(rowAttribute);
            }
        }catch (Exception e){
            LOGGER.error("Error during import Attributes for row {}", rowId);
            throw new IllegalArgumentException("Error during importing one of attributes!");
        }

    }

    private DataToConvert prepareDataToConvert(ImportedDataModel impData){
        DataToConvert dataToConvert = new DataToConvert();
        dataToConvert.setImportedDataModel(impData);
        List<ImportedRow> rows = importedRowRepository.getAllByImportIdOrderByRowId(impData.getId());
        dataToConvert.setImportedRowList(rows);
        List<RowAttribute> attributes = new ArrayList<>();
        rows.stream().forEach(row-> {
                List<RowAttribute> foundAttr =
                        rowAttributesRepository.getAllByCopyRowIdOrderByCopyRowId(row.getRowId());
                attributes.addAll(foundAttr);
        } );
        dataToConvert.setRowAttributes(attributes);
        return dataToConvert;
    }

    private void markImportProcessed(int importId, ConvertedDataInfo.ALGORITHM algorithm){
        ImportedDataModel importedData = importedDataRepository.getById(importId);
        if(algorithm == ConvertedDataInfo.ALGORITHM.TSNE){
            importedData.setConvertedByTSNE(true);
        }else if(algorithm == ConvertedDataInfo.ALGORITHM.PCA){
            importedData.setConvertedByPSA(true);
        }else {
            LOGGER.error("Algorithm not found, value: {}", algorithm.toString());
        }
        importedDataRepository.save(importedData);
    }



}
