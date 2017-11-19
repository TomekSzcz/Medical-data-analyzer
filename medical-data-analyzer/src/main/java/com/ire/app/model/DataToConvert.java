package com.ire.app.model;

import com.ire.app.model.entity.ImportedDataModel;
import com.ire.app.model.entity.ImportedRow;
import com.ire.app.model.entity.RowAttribute;

import java.util.List;

public class DataToConvert {
    private ImportedDataModel importedDataModel;
    private List<ImportedRow> importedRowList;
    private List<RowAttribute> rowAttributes;


    public ImportedDataModel getImportedDataModel() {
        return importedDataModel;
    }

    public void setImportedDataModel(ImportedDataModel importedDataModel) {
        this.importedDataModel = importedDataModel;
    }

    public List<ImportedRow> getImportedRowList() {
        return importedRowList;
    }

    public void setImportedRowList(List<ImportedRow> importedRowList) {
        this.importedRowList = importedRowList;
    }

    public List<RowAttribute> getRowAttributes() {
        return rowAttributes;
    }

    public void setRowAttributes(List<RowAttribute> rowAttributes) {
        this.rowAttributes = rowAttributes;
    }
}
