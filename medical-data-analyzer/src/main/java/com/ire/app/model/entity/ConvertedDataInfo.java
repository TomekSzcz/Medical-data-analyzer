package com.ire.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "converted_data_info")
public class ConvertedDataInfo implements Serializable {

    @Id
    private int id;
    private int importId;
    private String algorithm;
    private String fileName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public ALGORITHM getAlgorithm() {
        if (ALGORITHM.PCA.getAlgorithmName().equals(algorithm)) {
            return ALGORITHM.PCA;
        } else if (ALGORITHM.TSNE.getAlgorithmName().equals(algorithm)) {
            return ALGORITHM.TSNE;
        } else if (ALGORITHM.LLE.getAlgorithmName().equals(algorithm)) {
            return ALGORITHM.LLE;
        } else {
            return ALGORITHM.UNKNOWN;
        }
    }

    public void setAlgorithm(ALGORITHM algorithm) {
        this.algorithm = algorithm.getAlgorithmName();
    }

    public enum ALGORITHM {
        PCA("pca"),
        TSNE("tsne"),
        LLE("lle"),
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
