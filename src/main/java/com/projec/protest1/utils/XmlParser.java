package com.projec.protest1.utils;

import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.exception.ApiErrorCodeException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    // 사용하는 공공 교통 API는 XML로 결과를 돌려준다.
    //작업을 편하게 하기 위해서 JSON으로 변환하여 사용
    String spotInfoType = "SpotInfo";
    String volInfoType = "VolInfo";

    public List<RoadSpotInfo> xmlStringToRoadDto(String httpBody, String requestUrl) throws ApiErrorCodeException {
        JSONObject httpBodyJsonObject = XML.toJSONObject(httpBody);
        checkErrorCode(spotInfoType, httpBodyJsonObject, requestUrl);
        JSONArray jsonArray = httpBodyJsonObject.getJSONObject(spotInfoType).getJSONArray("row");

        List<RoadSpotInfo> roadSpotInfos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadSpotInfos.add(new RoadSpotInfo(tempObj.getString("spot_num"), tempObj.getString("spot_nm")));
        }
        return roadSpotInfos;
    }

    public List<RoadInfoDto> xmlStringToRoadInfoDto(String httpBody, String requestUrl) throws ApiErrorCodeException {
        JSONObject httpBodyJsonObject = XML.toJSONObject(httpBody);
        checkErrorCode(volInfoType, httpBodyJsonObject, requestUrl);
        JSONArray jsonArray = httpBodyJsonObject.getJSONObject(volInfoType).getJSONArray("row");

        List<RoadInfoDto> roadInfoDtoList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            roadInfoDtoList.add(new RoadInfoDto(tempObj.getInt("io_type"), tempObj.getInt("lane_num"), tempObj.getInt("vol")));
        }
        return roadInfoDtoList;
    }

    private void checkErrorCode(String infoType, JSONObject jsonObject, String requestUrl) throws ApiErrorCodeException {
        JSONObject result = jsonObject.getJSONObject(infoType).getJSONObject("RESULT");
        String code = (String) result.get("CODE");
        String message = (String) result.get("MESSAGE");
        if (code.equals("INFO-000")) {
            return;
        }
        throw new ApiErrorCodeException(requestUrl + "|" + code + "|" + message);
    }
}
