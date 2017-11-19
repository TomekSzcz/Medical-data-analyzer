package com.ire.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ConvertedDataRowsPK implements Serializable{

    @Column(name = "import_id")
    private int importId;
    @Column(name = "id")
    private int id;

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
