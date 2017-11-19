package com.ire.app.model;

import java.util.List;

public class DataForAlgorithm {
    private double[][] data;
    private List<String> diagnoses;

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }
}
