package com.projec.protest1.utils;

public class UrlMaker {
    String openApiUrl = "http://openapi.seoul.go.kr:8088/";
    String key = "7944415075796f75393267765a5967";
    String baseUrl = openApiUrl + key + "/xml/";

    public String getSpotInfoUrl() {
        int start = 1;
        int end = 169;
        String str = "SpotInfo";
        return baseUrl + str + "/" + start + "/" + end;
    }

    public String getVolInfoUrl(String rid, String yyyymmdd, Integer time) {
        int start = 1;
        int end = 10;
        String str = "VolInfo";
        return baseUrl + str + "/" + start + "/" + end + "/" +
                rid + "/" + yyyymmdd + "/" + String.format("%02d", time);
    }
}
