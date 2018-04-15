package com.ire.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "original_data_for_tooltip")
public class OriginalDataForTooltip {
    @Id
    private int originalDataRowId;
    private String tooltipText;

    public  OriginalDataForTooltip(){

    }

    public OriginalDataForTooltip(int originalDataRowId, String tooltipText){
        this.originalDataRowId = originalDataRowId;
        this.tooltipText = tooltipText;
    }

    public int getOriginalDataRowId() {
        return originalDataRowId;
    }

    public void setOriginalDataRowId(int originalDataRowId) {
        this.originalDataRowId = originalDataRowId;
    }


    public String getTooltipText() {
        return tooltipText;
    }

    public void setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }
}
