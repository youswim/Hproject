package com.projec.protest1.utils;

import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.dto.RoadInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XmlParser {
    // 사용하는 공공 교통 API는 XML로 결과를 돌려준다.
    //작업을 편하게 하기 위해서 JSON으로 변환하여 사용
    UrlRequester ur = new UrlRequester();
    String spotInfo = "SpotInfo";
    String volInfo = "VolInfo";

    public List<RoadDto> formXmlToRoadDto(String url) {
        List<RoadDto> roadDtoList = new ArrayList<>();
        JSONObject jsonObject = XML.toJSONObject(ur.requestXml(url));
        if (hasErrorCode(url, spotInfo, jsonObject)) {
            return roadDtoList;
        }
        JSONArray jsonArray = jsonObject.getJSONObject(spotInfo).getJSONArray("row");


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadDtoList.add(new RoadDto(tempObj));
        }
        return roadDtoList;
    }

    public List<RoadInfoDto> fromXmlToRoadInfoDto(String url) {
        List<RoadInfoDto> roadInfoDtoList = new ArrayList<>();
        JSONObject jsonObject = XML.toJSONObject(ur.requestXml(url));
        if (hasErrorCode(url, volInfo, jsonObject)) {
            return roadInfoDtoList;
        }
        JSONArray jsonArray = jsonObject.getJSONObject(volInfo).getJSONArray("row");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadInfoDtoList.add(new RoadInfoDto(tempObj));
        }
        return roadInfoDtoList;
    }

    private boolean hasErrorCode(String url, String info, JSONObject jsonObject) {
        String code = (String) jsonObject.getJSONObject(info).getJSONObject("RESULT").get("CODE");
        if (code.equals("INFO-000")) {
            return false;
        }
        log.warn("url:" + url + " " + "jsonObject:" + jsonObject);
        return true;
    }
}
