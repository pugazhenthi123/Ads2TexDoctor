package com.ads2tex.ads2texdoctor.Pojo;

public class PatientDrugs {
    private int sno;
    private String name,date,unit,quantity;

    public PatientDrugs(int sno, String name, String date, String unit, String quantity) {
        this.sno = sno;
        this.name = name;
        this.date = date;
        this.unit = unit;
        this.quantity = quantity;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
