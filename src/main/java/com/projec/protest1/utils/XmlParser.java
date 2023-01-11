package com.projec.protest1.utils;

import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.dto.RoadInfoDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    // 사용하는 공공 교통 API는 XML로 결과를 돌려준다.
    //작업을 편하게 하기 위해서 JSON으로 변환하여 사용
    UrlRequester ur = new UrlRequester();

    public List<RoadDto> formJsonToRoadDto(String url) {

        JSONObject jsonObject = XML.toJSONObject(ur.requestXml(url));
        JSONArray jsonArray = jsonObject.getJSONObject("SpotInfo").getJSONArray("row");

        List<RoadDto> roadDtoList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadDtoList.add(new RoadDto(tempObj));
        }
        return roadDtoList;
    }

    public List<RoadInfoDto> fromJsonToRoadInfoDto(String url) {
        JSONObject jsonObject = XML.toJSONObject(ur.requestXml(url));
        JSONArray jsonArray = jsonObject.getJSONObject("VolInfo").getJSONArray("row");

        List<RoadInfoDto> roadInfoDtoList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadInfoDtoList.add(new RoadInfoDto(tempObj));
        }
        return roadInfoDtoList;
    }
}