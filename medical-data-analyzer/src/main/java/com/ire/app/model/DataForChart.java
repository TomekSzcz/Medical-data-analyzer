package com.ire.app.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class DataForChart {
    private final double axisX;
    private final double axisY;
    private final String diagnosis;

    public DataForChart(double axisX, double axisY, String diagnosis) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.diagnosis = diagnosis;
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
}