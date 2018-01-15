package com.ire.app.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class AlgorithmConfigData {

    private String algorithmName;
    private int maxIteration;
    private double perplexity;
    private boolean usePCA;
    private double theta;

    public AlgorithmConfigData(String algorithmName, int maxIteration,
                               double perplexity, double theta, boolean usePCA){
        this.algorithmName = algorithmName;
        this.maxIteration = maxIteration;
        this.perplexity = perplexity;
        this.usePCA = usePCA;
        this.theta = theta;
    }

    public AlgorithmConfigData(){
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
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

    public boolean getUsePCA() {
        return usePCA;
    }

    public void setUsePCA(boolean usePCA) {
        this.usePCA = usePCA;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

}
