package com.ads2tex.ads2texdoctor.Pojo;

public class Patient_History {
    int tempurature,sugar,pressure;
    String date;

    public Patient_History(String date, int tempurature, int sugar, int pressure) {
        this.date = date;
        this.tempurature = tempurature;
        this.sugar = sugar;
        this.pressure = pressure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTempurature() {
        return tempurature;
    }

    public void setTempurature(int tempurature) {
        this.tempurature = tempurature;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
}
