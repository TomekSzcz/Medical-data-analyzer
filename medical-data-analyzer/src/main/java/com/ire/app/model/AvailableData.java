package com.ire.app.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonSerialize
public class AvailableData {
    private final String fileName;
    private final int importId;
    private final boolean isTSNEavailable;
    private final boolean isPSAavailable;
    private final String importDate;
    private final DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    public AvailableData(String fileName, int importId, boolean isTSNEavailable, boolean isPSAavailable, Date importDate) {
        this.fileName = fileName;
        this.importId = importId;
        this.isTSNEavailable = isTSNEavailable;
        this.isPSAavailable = isPSAavailable;
        this.importDate = dateFormat.format(importDate);
    }

    public String getFileName() {
        return fileName;
    }

    public int getImportId() {
        return importId;
    }

    public boolean isTSNEavailable() {
        return isTSNEavailable;
    }

    public boolean isPSAavailable() {
        return isPSAavailable;
    }

    public String getImportDate() {
        return importDate;
    }
}
