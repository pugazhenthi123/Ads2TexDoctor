package com.ads2tex.ads2texdoctor.Pojo;

public class MultiDrugs {
    private String name;
    private Boolean check;
    public MultiDrugs(String name, Boolean check)
    {
        super();
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
