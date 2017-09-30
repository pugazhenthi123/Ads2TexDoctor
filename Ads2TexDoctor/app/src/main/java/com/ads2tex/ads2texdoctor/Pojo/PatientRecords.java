package com.ads2tex.ads2texdoctor.Pojo;


public class PatientRecords {
    private int sno,age,weight,noofchecks,temperature,pressure,sugar;
    private String name,diseases,date,next_checkup_date,status,status_color,description;
    private Boolean check;

    public PatientRecords() {
    }

    public PatientRecords(int sno,String name, int age, int weight, int noofchecks, int temperature, int pressure, int sugar, String diseases, String date, String next_checkup_date, String status,String status_color,String description,Boolean check) {
        this.sno = sno;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.noofchecks = noofchecks;
        this.temperature = temperature;
        this.pressure = pressure;
        this.sugar = sugar;
        this.diseases = diseases;
        this.date = date;
        this.next_checkup_date = next_checkup_date;
        this.status = status;
        this.status_color = status_color;
        this.description = description;
        this.check = check;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus_color() {
        return status_color;
    }

    public void setStatus_color(String status_color) {
        this.status_color = status_color;
    }

    public int getNoofchecks() {
        return noofchecks;
    }

    public void setNoofchecks(int noofchecks) {
        this.noofchecks = noofchecks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNext_checkup_date() {
        return next_checkup_date;
    }

    public void setNext_checkup_date(String next_checkup_date) {
        this.next_checkup_date = next_checkup_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}