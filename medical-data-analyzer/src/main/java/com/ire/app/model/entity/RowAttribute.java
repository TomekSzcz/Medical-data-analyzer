package com.ire.app.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "row_attribute")
public class RowAttribute {

    @Id
    RowAttributePK rowAttributePK;

    private double value;
    private String attributeName;

    private int copyRowId;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public RowAttributePK getRowAttributePK() {
        return rowAttributePK;
    }

    public void setRowAttributePK(RowAttributePK rowAttributePK) {
        this.rowAttributePK = rowAttributePK;
        this.copyRowId = rowAttributePK.getRowId();
    }

    public int getCopyRowId() {
        return copyRowId;
    }

    public void setCopyRowId(int copyRowId) {
        this.copyRowId = copyRowId;
    }
}
