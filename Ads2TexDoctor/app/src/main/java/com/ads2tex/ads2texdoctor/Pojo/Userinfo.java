package com.ads2tex.ads2texdoctor.Pojo;

import java.util.ArrayList;
import java.util.List;

public class Userinfo {
    private static String username = "", password = "", userid = "";
    private static List<Integer> Patient_no_List = new ArrayList<>();
    private static List<String> Patient_name_List = new ArrayList<>();
    private static List<Integer> drug_no_List = new ArrayList<>();
    private static List<String> drug_name_List = new ArrayList<>();
    private static List<Integer> drug_manufacturer_no_List = new ArrayList<>();
    private static List<String> drug_manufacturer_name_List = new ArrayList<>();
    private static List<Integer> drug_generic_no_List = new ArrayList<>();
    private static List<String> drug_generic_name_List = new ArrayList<>();
    private static List<Integer> drug_unit_no_List = new ArrayList<>();
    private static List<String> drug_unit_name_List = new ArrayList<>();
    private static List<String> patient_status_List = new ArrayList<>();
    private static List<Integer> patient_status_no_List = new ArrayList<>();
    private static List<String> patient_status_color_List = new ArrayList<>();
    private static List<Integer> drug_type_no_List = new ArrayList<>();
    private static List<String> drug_type_name_List = new ArrayList<>();
    private static List<Integer> disease_no_List = new ArrayList<>();
    private static List<String> disease_name_List = new ArrayList<>();

    private static int navigation = 11,hospital=1,npos=0,home_today_qty_list_size=0;
    private static String imei = "";
    private static Boolean check = false,update=false;
    private static String version = "", About = "", Applink = "", Shareapp, cboard = "",logo_link="";

    public static Boolean getUpdate() {
        return update;
    }

    public static void setUpdate(Boolean update) {
        Userinfo.update = update;
    }

    public Userinfo() {

    }

    public static List<Integer> getDrug_no_List() {
        return drug_no_List;
    }

    public static void setDrug_no_List(List<Integer> drug_no_List) {
        Userinfo.drug_no_List = drug_no_List;
    }

    public static List<String> getDrug_name_List() {
        return drug_name_List;
    }

    public static void setDrug_name_List(List<String> drug_name_List) {
        Userinfo.drug_name_List = drug_name_List;
    }

    public static List<Integer> getDrug_manufacturer_no_List() {
        return drug_manufacturer_no_List;
    }

    public static void setDrug_manufacturer_no_List(List<Integer> drug_manufacturer_no_List) {
        Userinfo.drug_manufacturer_no_List = drug_manufacturer_no_List;
    }

    public static List<String> getDrug_manufacturer_name_List() {
        return drug_manufacturer_name_List;
    }

    public static void setDrug_manufacturer_name_List(List<String> drug_manufacturer_name_List) {
        Userinfo.drug_manufacturer_name_List = drug_manufacturer_name_List;
    }

    public static List<Integer> getDrug_generic_no_List() {
        return drug_generic_no_List;
    }

    public static void setDrug_generic_no_List(List<Integer> drug_generic_no_List) {
        Userinfo.drug_generic_no_List = drug_generic_no_List;
    }

    public static List<String> getDrug_generic_name_List() {
        return drug_generic_name_List;
    }

    public static void setDrug_generic_name_List(List<String> drug_generic_name_List) {
        Userinfo.drug_generic_name_List = drug_generic_name_List;
    }

    public static List<String> getDisease_name_List() {
        return disease_name_List;
    }

    public static void setDisease_name_List(List<String> disease_name_List) {
        Userinfo.disease_name_List = disease_name_List;
    }

    public static int getHome_today_qty_list_size() {
        return home_today_qty_list_size;
    }

    public static void setHome_today_qty_list_size(int home_today_qty_list_size) {
        Userinfo.home_today_qty_list_size = home_today_qty_list_size;
    }

    public static List<Integer> getDisease_no_List() {
        return disease_no_List;
    }

    public static void setDisease_no_List(List<Integer> disease_no_List) {
        Userinfo.disease_no_List = disease_no_List;
    }

    public static List<Integer> getDrug_type_no_List() {
        return drug_type_no_List;
    }

    public static void setDrug_type_no_List(List<Integer> drug_type_no_List) {
        Userinfo.drug_type_no_List = drug_type_no_List;
    }

    public static List<String> getDrug_type_name_List() {
        return drug_type_name_List;
    }

    public static void setDrug_type_name_List(List<String> drug_type_name_List) {
        Userinfo.drug_type_name_List = drug_type_name_List;
    }


    public static List<String> getPatient_status_color_List() {
        return patient_status_color_List;
    }

    public static void setPatient_status_color_List(List<String> patient_status_color_List) {
        Userinfo.patient_status_color_List = patient_status_color_List;
    }

    public static void setImei(String imei) {
        Userinfo.imei = imei;
    }

    public static String getAbout() {
        return About;
    }

    public static String getShareapp() {
        return Shareapp;
    }

    public static String getCboard() {
        return cboard;
    }

    public static void setCboard(String cboard) {
        Userinfo.cboard = cboard;
    }

    public static int getHospital() {
        return hospital;
    }

    public static void setHospital(int hospital) {
        Userinfo.hospital = hospital;
    }

    public static String getLogo_link() {
        return logo_link;
    }

    public static void setLogo_link(String logo_link) {
        Userinfo.logo_link = logo_link;
    }

    public static String getImei() {
        return imei;
    }

    public static void setShareapp(String shareapp) {
        Shareapp = shareapp;
    }

    public static Boolean getCheck() {
        return check;
    }

    public static String getApplink() {
        return Applink;
    }

    public static void setAbout(String about) {
        About = about;
    }

    public static void setApplink(String applink) {
        Applink = applink;
    }

    public static void setCheck(Boolean check) {
        Userinfo.check = check;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        Userinfo.version = version;
    }

    public static void ClearAllList() {
        patient_status_List.clear();
        patient_status_no_List.clear();
        drug_unit_name_List.clear();
        drug_unit_no_List.clear();
        patient_status_no_List.clear();
        patient_status_List.clear();
        patient_status_color_List.clear();
        drug_type_name_List.clear();
        drug_type_no_List.clear();
        disease_name_List.clear();
        disease_no_List.clear();
        drug_manufacturer_no_List.clear();
        drug_manufacturer_name_List.clear();
        drug_generic_no_List.clear();
        drug_generic_name_List.clear();
        drug_no_List.clear();
        drug_name_List.clear();
    }

    public static List<Integer> getPatient_no_List() {
        return Patient_no_List;
    }

    public static void setPatient_no_List(List<Integer> patient_no_List) {
        Patient_no_List = patient_no_List;
    }

    public static List<String> getPatient_name_List() {
        return Patient_name_List;
    }

    public static void setPatient_name_List(List<String> patient_name_List) {
        Patient_name_List = patient_name_List;
    }

    public static List<Integer> getDrug_unit_no_List() {
        return drug_unit_no_List;
    }

    public static void setDrug_unit_no_List(List<Integer> drug_unit_no_List) {
        Userinfo.drug_unit_no_List = drug_unit_no_List;
    }

    public static List<String> getDrug_unit_name_List() {
        return drug_unit_name_List;
    }

    public static void setDrug_unit_name_List(List<String> drug_unit_name_List) {
        Userinfo.drug_unit_name_List = drug_unit_name_List;
    }

    public static List<String> getPatient_status_List() {
        return patient_status_List;
    }

    public static void setPatient_status_List(List<String> patient_status_List) {
        Userinfo.patient_status_List = patient_status_List;
    }

    public static List<Integer> getPatient_status_no_List() {
        return patient_status_no_List;
    }

    public static void setPatient_status_no_List(List<Integer> patient_status_no_List) {
        Userinfo.patient_status_no_List = patient_status_no_List;
    }

    public static int getNavigation() {
        return navigation;
    }

    public static void setNavigation(int navigation) {
        Userinfo.navigation = navigation;
    }

    public static int getNpos() {
        return npos;
    }

    public static void setNpos(int npos) {
        Userinfo.npos = npos;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        Userinfo.userid = userid;
    }

    public Userinfo(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Userinfo.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Userinfo.password = password;
    }

}

