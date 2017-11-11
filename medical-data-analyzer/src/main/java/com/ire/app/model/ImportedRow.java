package com.ire.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "imported_row")
public class ImportedRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rowId;
    private int importId;
    private String diagnosis;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public Enum getDiagnosis() {
        if(DIAGNOSIS.SICK.getDiagnosis().equals(diagnosis)){
            return DIAGNOSIS.SICK;
        }else if(DIAGNOSIS.SICK.getDiagnosis().equals(diagnosis)) {
            return DIAGNOSIS.HEALTHY;
        }else {
            return DIAGNOSIS.UNKNOWN;
        }
    }

    public void setDiagnosis(DIAGNOSIS diagnosis) {
        this.diagnosis = diagnosis.toString();
    }

    public enum DIAGNOSIS {
        SICK("sick"),
        HEALTHY("healthy"),
        UNKNOWN("unknown");

        public String diagnosis;

        DIAGNOSIS(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public String getDiagnosis() {
            return diagnosis;
        }

        @Override
        public String toString() {
            return getDiagnosis();
        }
    }
}
