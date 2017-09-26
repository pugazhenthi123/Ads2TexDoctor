package com.ads2tex.ads2texdoctor.Pojo;

public class Home_Dialog_Drug_Select_Unit {
    int sno,unit,qty;
    String name;

    public Home_Dialog_Drug_Select_Unit(int sno, int unit, String name, int qty) {
        this.sno = sno;
        this.unit = unit;
        this.name = name;
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
