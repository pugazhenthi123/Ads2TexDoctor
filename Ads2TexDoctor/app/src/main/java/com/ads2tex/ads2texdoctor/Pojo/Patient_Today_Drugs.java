package com.ads2tex.ads2texdoctor.Pojo;


public class Patient_Today_Drugs {
    int drug_no,drug_unit,drug_quantity;

    public Patient_Today_Drugs(int drug_no, int drug_unit, int drug_quantity) {
        this.drug_no = drug_no;
        this.drug_unit = drug_unit;
        this.drug_quantity = drug_quantity;
    }

    public int getDrug_no() {
        return drug_no;
    }

    public void setDrug_no(int drug_no) {
        this.drug_no = drug_no;
    }

    public int getDrug_unit() {
        return drug_unit;
    }

    public void setDrug_unit(int drug_unit) {
        this.drug_unit = drug_unit;
    }

    public int getDrug_quantity() {
        return drug_quantity;
    }

    public void setDrug_quantity(int drug_quantity) {
        this.drug_quantity = drug_quantity;
    }
}
