package com.ire.app.model;

import java.util.List;

public class DataForAlgorithm {
    private double[][] data;
    private List<String> diagnoses;
    private List<Integer> originalDataRowIds;

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

    public List<Integer> getOriginalDataRowIds() {
        return originalDataRowIds;
    }

    public void setOriginalDataRowIds(List<Integer> originalDataRowIds) {
        this.originalDataRowIds = originalDataRowIds;
    }
}
