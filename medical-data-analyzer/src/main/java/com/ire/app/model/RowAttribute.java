package com.ire.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "row_attribute")
public class RowAttribute {

    @Id
    RowAttributePK rowAttributePK;

    private double value;
    private String attributeName;


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

    public void setRowAttributePK(RowAttributePK rowAttributePK){
        this.rowAttributePK = rowAttributePK;
    }
}
