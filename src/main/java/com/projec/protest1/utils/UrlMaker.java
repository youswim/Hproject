package com.projec.protest1.utils;

public class UrlMaker {
    String openApiUrl = "http://openapi.seoul.go.kr:8088/";
    String key = "7944415075796f75393267765a5967";
    String baseUrl = openApiUrl + key + "/xml/";

    public String getSpotInfoUrl() {
        int start = 1;
        int end = 169;
        String str = "SpotInfo";
        StringBuilder requestUrl = new StringBuilder()
                .append(baseUrl).append(str).append("/").append(start).append("/").append(end);
        return requestUrl.toString();
    }

    public String getVolInfoUrl(String rid, int yyyymmdd, int time) {
        int start = 1;
        int end = 10;
        String str = "VolInfo";
        StringBuilder requestUrl = new StringBuilder()
                .append(baseUrl).append(str).append("/").append(start).append("/").append(end).append("/")
                .append(rid).append("/").append(yyyymmdd).append("/").append(time);
        return requestUrl.toString();
    }
}
