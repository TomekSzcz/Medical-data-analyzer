package com.ire.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "converted_data_info")
public class ConvertedDataInfo {
    @Id
    private int importId;
    private String fileName;
    private String algorithm;

    public enum ALGORITHM {
        PCA("pca"),
        TSNE("tsne"),
        UNKNOWN("unknown");

        private String algorithmName;

        ALGORITHM(String algorithmName) {
            this.algorithmName = algorithmName;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }

        @Override
        public String toString() {
            return getAlgorithmName();
        }
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ALGORITHM getAlgorithm() {
        if(ALGORITHM.PCA.getAlgorithmName().equals(algorithm)){
            return ALGORITHM.PCA;
        }else if(ALGORITHM.TSNE.getAlgorithmName().equals(algorithm)){
            return ALGORITHM.TSNE;
        }else {
            return ALGORITHM.UNKNOWN;
        }
    }

    public void setAlgorithm(ALGORITHM algorithm) {
        this.algorithm = algorithm.getAlgorithmName();
    }

}
