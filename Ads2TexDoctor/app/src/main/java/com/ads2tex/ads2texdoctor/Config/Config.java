package com.ads2tex.ads2texdoctor.Config;


public class Config {

    public static String SERVER_ADDRESS = "http://www.ads2tex.in/doctor/AndroidServer";
    public static String BANNER_ADDRESS = "http://www.ads2tex.in/doctor/AndroidServer/slider/";
    public static String LOGO_ADDRESS = "http://www.ads2tex.in/doctor/AndroidServer/logo/";

    public static String getServer_address() {
        return SERVER_ADDRESS;
    }

    public static String getLogoAddress() {
        return LOGO_ADDRESS;
    }

    public static void setLogoAddress(String logoAddress) {
        LOGO_ADDRESS = logoAddress;
    }

    public static void setServer_address(String server_address) {
        Config.SERVER_ADDRESS = server_address;
    }

    public static String getBannerAddress() {
        return BANNER_ADDRESS;
    }

    public static void setBannerAddress(String bannerAddress) {
        BANNER_ADDRESS = bannerAddress;
    }
}
