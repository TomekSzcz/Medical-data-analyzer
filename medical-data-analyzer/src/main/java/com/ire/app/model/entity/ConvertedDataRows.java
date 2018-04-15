package com.ire.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "converted_data_rows")
public class ConvertedDataRows {

    @Id
    private ConvertedDataRowsPK convertedDataRowsPK;
    private String diagnosis;
    private double axisX;
    private double axisY;
    private int copyId;
    private int originalDataRowId;


    public ConvertedDataRowsPK getConvertedDataRowsPK() {
        return convertedDataRowsPK;
    }

    public void setConvertedDataRowsPK(ConvertedDataRowsPK convertedDataRowsPK) {
        this.convertedDataRowsPK = convertedDataRowsPK;
        this.copyId = convertedDataRowsPK.getId();
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public double getAxisX() {
        return axisX;
    }

    public void setAxisX(double axisX) {
        this.axisX = axisX;
    }

    public double getAxisY() {
        return axisY;
    }

    public void setAxisY(double axisY) {
        this.axisY = axisY;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public int getOriginalDataRowId() {
        return originalDataRowId;
    }

    public void setOriginalDataRowId(int originalDataRowId) {
        this.originalDataRowId = originalDataRowId;
    }
}
