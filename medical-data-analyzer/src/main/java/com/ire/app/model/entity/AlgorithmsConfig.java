package com.ire.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "algorithm_config")
public class AlgorithmsConfig {
    @Id
    private int id;
    private String algorithmName;
    private int maxIteration;
    private double perplexity;
    private int usePca;
    private double theta;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConvertedDataInfo.ALGORITHM getAlgorithmName() {
        if(ConvertedDataInfo.ALGORITHM.PCA.getAlgorithmName().equals(algorithmName)){
            return ConvertedDataInfo.ALGORITHM.PCA;
        }else if(ConvertedDataInfo.ALGORITHM.TSNE.getAlgorithmName().equals(algorithmName)){
            return ConvertedDataInfo.ALGORITHM.TSNE;
        }else {
            return ConvertedDataInfo.ALGORITHM.UNKNOWN;
        }
    }

    public void setAlgorithmName(ConvertedDataInfo.ALGORITHM algorithm) {
        this.algorithmName = algorithm.getAlgorithmName();
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
        this.maxIteration = maxIteration;
    }

    public double getPerplexity() {
        return perplexity;
    }

    public void setPerplexity(double perplexity) {
        this.perplexity = perplexity;
    }

    public boolean getUsePca() {
        if(usePca == 1){
            return true;
        }else {
            return false;
        }
    }

    public void setUsePca(boolean usePca) {
        if(usePca == true){
            this.usePca = 1;
        }else {
            this.usePca = 0;
        }
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }
}
