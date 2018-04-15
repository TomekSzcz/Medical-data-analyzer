package com.ire.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ConvertedDataRowsPK implements Serializable{

    @Column(name = "converted_data_id")
    private int convertedDataId;
    @Column(name = "id")
    private int id;

    public int getConvertedDataId() {
        return convertedDataId;
    }

    public void setConvertedDataId(int convertedDataId) {
        this.convertedDataId = convertedDataId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
