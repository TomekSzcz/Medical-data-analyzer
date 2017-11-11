package com.ire.app.service.impl;

import com.ire.app.model.ImportedDataModel;
import com.ire.app.model.ImportedRow;
import com.ire.app.model.RowAttribute;
import com.ire.app.model.RowAttributePK;
import com.ire.app.repository.ImportedDataRepository;
import com.ire.app.repository.ImportedRowRepository;
import com.ire.app.repository.RowAttributesRepository;
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
import java.math.BigDecimal;
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



}
