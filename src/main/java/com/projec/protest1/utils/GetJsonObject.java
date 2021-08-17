package com.projec.protest1.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.projec.protest1.domain.RoadDto;
import com.projec.protest1.domain.RoadInfoDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetJsonObject {

    public JSONObject getJsonObject(String url) throws JsonProcessingException {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange(url, HttpMethod.GET, requestEntity, String.class);

        String xmlString = responseEntity.getBody();
        return XML.toJSONObject(xmlString);
    }

    public List<RoadDto> formJsonToRoadDto(JSONObject jsonObject, String str) {
        JSONObject parsed_Obj = jsonObject.getJSONObject(str);

        JSONArray jsonArray = parsed_Obj.getJSONArray("row");
        List<RoadDto> roadDtoList = new ArrayList<>();

        RoadDto roadDto = new RoadDto();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            System.out.println(tempObj);
            roadDto = new RoadDto(tempObj);

            roadDtoList.add(roadDto);
        }
        return roadDtoList;
    }

    public List<RoadInfoDto> fromJsonToRoadInfoDto(JSONObject jsonObject, String str){

        JSONObject parsed_Obj = jsonObject.getJSONObject(str);

        JSONArray jsonArray = parsed_Obj.getJSONArray("row");
        List<RoadInfoDto> roadInfoDtoList = new ArrayList<>();

        RoadInfoDto roadInfoDto = new RoadInfoDto();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            System.out.println(tempObj);
            roadInfoDto = new RoadInfoDto(tempObj);

            roadInfoDtoList.add(roadInfoDto);
        }
        return roadInfoDtoList;
    }

//    public static void main(String[] args) throws JsonProcessingException {
//
//        GetJsonObject getJsonObject = new GetJsonObject();
//        JSONObject jsonObject = getJsonObject.getJsonObject();
//        System.out.println(getJsonObject.formJsonToItems(jsonObject));
//
//        long time = System.currentTimeMillis() + 43200000;
//        System.out.println(time);
//        SimpleDateFormat simpl = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초");
//        String s = simpl.format(time);
//
//        System.out.println(s);
//
//        Date now = new Date();
//        Calendar cal = Calendar.getInstance();
//        System.out.println(cal);
//
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH) + 1;
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int min = cal.get(Calendar.MINUTE);
//        int sec = cal.get(Calendar.SECOND);
//        System.out.println("현재 시각은 " + year + "년도 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 " + sec + "초입니다.");
//
//
//    }
//
}
