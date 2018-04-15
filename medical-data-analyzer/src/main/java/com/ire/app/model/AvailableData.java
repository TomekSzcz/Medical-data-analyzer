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
    private final boolean isPCAavailable;
    private final boolean isLLEAvailable;
    private final String importDate;
    private final DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    public AvailableData(String fileName, int importId, boolean isTSNEAvailable, boolean isPCAAvailable,
                         boolean isLLEAvailable, Date importDate) {
        this.fileName = fileName;
        this.importId = importId;
        this.isTSNEavailable = isTSNEAvailable;
        this.isPCAavailable = isPCAAvailable;
        this.isLLEAvailable = isLLEAvailable;
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

    public boolean isPCAavailable() {
        return isPCAavailable;
    }

    public String getImportDate() {
        return importDate;
    }
}
