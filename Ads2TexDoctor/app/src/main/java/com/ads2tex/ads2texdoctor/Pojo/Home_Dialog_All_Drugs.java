package com.ads2tex.ads2texdoctor.Pojo;

import java.util.List;

public class Home_Dialog_All_Drugs {
    private int sno,unit_pos;
    private String name,generic,manufacturer,units,diseases;
    private Boolean check,compare_check;
    private List<Home_Dialog_Drug_Unit> home_dialog_drug_unitList;

    public Home_Dialog_All_Drugs(int sno, String name, String generic, String manufacturer, String units, String diseases, List<Home_Dialog_Drug_Unit> home_dialog_drug_unitList, Boolean check, Boolean compare_check, int unit_pos) {
        this.sno = sno;
        this.name = name;
        this.generic = generic;
        this.manufacturer = manufacturer;
        this.units = units;
        this.diseases = diseases;
        this.home_dialog_drug_unitList = home_dialog_drug_unitList;
        this.check = check;
        this.compare_check = compare_check;
        this.unit_pos = unit_pos;
    }

    public Boolean getCompare_check() {
        return compare_check;
    }

    public void setCompare_check(Boolean compare_check) {
        this.compare_check = compare_check;
    }

    public int getUnit_pos() {
        return unit_pos;
    }

    public void setUnit_pos(int unit_pos) {
        this.unit_pos = unit_pos;
    }

    public List<Home_Dialog_Drug_Unit> getHome_dialog_drug_unitList() {
        return home_dialog_drug_unitList;
    }

    public void setHome_dialog_drug_unitList(List<Home_Dialog_Drug_Unit> home_dialog_drug_unitList) {
        this.home_dialog_drug_unitList = home_dialog_drug_unitList;
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

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
