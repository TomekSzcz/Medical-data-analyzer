package com.ire.app.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "imported_data")
public class ImportedDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fileName;
    private int attributesNumber;
    private String importStatus;
    private Date importDate;

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public enum IMPORT_STATUS {
        IN_PROGRESS("inProgress"),
        FAILED("failed"),
        SUCCESS("success"),
        UNKNOWN("unknown");

        public String status;

        IMPORT_STATUS(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return getStatus();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getAttributesNumber() {
        return attributesNumber;
    }

    public void setAttributesNumber(int attributesNumber) {
        this.attributesNumber = attributesNumber;
    }

    public Enum getImportStatus() {
        if (IMPORT_STATUS.FAILED.getStatus().equals(importStatus)) {
            return IMPORT_STATUS.FAILED;
        } else if (IMPORT_STATUS.SUCCESS.getStatus().equals(importStatus)) {
            return IMPORT_STATUS.SUCCESS;
        } else if (IMPORT_STATUS.IN_PROGRESS.getStatus().equals(importStatus)) {
            return IMPORT_STATUS.IN_PROGRESS;
        } else {
            return IMPORT_STATUS.UNKNOWN;
        }
    }

    public void setImportStatus(Enum importStatus) {
        this.importStatus = importStatus.toString();
    }

}
