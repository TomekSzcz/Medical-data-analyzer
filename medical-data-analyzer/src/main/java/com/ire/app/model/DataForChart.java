package com.ire.app.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class DataForChart {
    private final int id;
    private final double axisX;
    private final double axisY;
    private final String diagnosis;
    private final String originalDataValues;

    public DataForChart(int id,double axisX, double axisY, String diagnosis, String originalDataValues) {
        this.id = id;
        this.axisX = axisX;
        this.axisY = axisY;
        this.diagnosis = diagnosis;
        this.originalDataValues = originalDataValues;
    }

    public double getAxisX() {
        return axisX;
    }

    public double getAxisY() {
        return axisY;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getOriginalDataValues() {
        return originalDataValues;
    }

    public int getId() {
        return id;
    }
}