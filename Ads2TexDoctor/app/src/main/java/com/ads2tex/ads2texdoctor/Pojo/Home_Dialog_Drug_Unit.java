package com.ads2tex.ads2texdoctor.Pojo;

public class Home_Dialog_Drug_Unit {
    int sno;
    Boolean check;

    public Home_Dialog_Drug_Unit(int sno, Boolean check) {
        this.sno = sno;
        this.check = check;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
