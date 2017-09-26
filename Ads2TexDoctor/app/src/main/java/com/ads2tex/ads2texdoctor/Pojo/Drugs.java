package com.ads2tex.ads2texdoctor.Pojo;


public class Drugs {
    private int sno;
    private String name,units,manufacturer,diseases,generic;

    public Drugs(int sno, String name, String units, String manufacturer, String diseases, String generic)
    {
        super();
        this.sno = sno;
        this.name = name;
        this.units = units;
        this.manufacturer = manufacturer;
        this.diseases = diseases;
        this.generic = generic;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }
}
