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
    HttpBodyRequester ur = new HttpBodyRequester();
    String spotInfo = "SpotInfo";
    String volInfo = "VolInfo";

    public List<RoadDto> xmlStringToRoadDto(String httpBody) {
        JSONObject httpBodyJsonObject = XML.toJSONObject(httpBody);
        checkErrorCode(spotInfo, httpBodyJsonObject);
        JSONArray jsonArray = httpBodyJsonObject.getJSONObject(spotInfo).getJSONArray("row");

        List<RoadDto> roadDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadDtoList.add(new RoadDto(tempObj));
        }
        return roadDtoList;
    }

    public List<RoadInfoDto> xmlStringToRoadInfoDto(String httpBody) {
        JSONObject httpBodyJsonObject = XML.toJSONObject(httpBody);
        checkErrorCode(volInfo, httpBodyJsonObject);
        JSONArray jsonArray = httpBodyJsonObject.getJSONObject(volInfo).getJSONArray("row");

        List<RoadInfoDto> roadInfoDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadInfoDtoList.add(new RoadInfoDto(tempObj));
        }
        return roadInfoDtoList;
    }

    private void checkErrorCode(String info, JSONObject jsonObject) {
        JSONObject result = jsonObject.getJSONObject(info).getJSONObject("RESULT");
        String code = (String) result.get("CODE");
        String message = (String) result.get("MESSAGE");
        if (code.equals("INFO-000")) {
            return;
        }
        throw new IllegalStateException(code + ":" + message);
    }
}
