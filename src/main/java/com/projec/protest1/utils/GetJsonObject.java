package com.projec.protest1.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.dto.RoadInfoDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class GetJsonObject {

    public JSONObject getJsonObject(String url) throws JsonProcessingException {
        //GET방식인 교통 api는 xml로 결과를 돌려준다.
        //작업을 편하게 하기 위해서 JSON으로 변환하는 과정이 필요하다
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String xmlString = responseEntity.getBody();
        //api에서 받아온 xml을 string으로 변환한다.

        System.out.println(XML.toJSONObject(xmlString));
        return XML.toJSONObject(xmlString);
        //xml을 JSONObject 로 변환해서 리턴한다.


    }

    public List<RoadDto> formJsonToRoadDto(JSONObject jsonObject) {


        JSONObject parsed_Object = jsonObject.getJSONObject("SpotInfo");
        //System.out.println(parsed_Object);

        JSONArray jsonArray = parsed_Object.getJSONArray("row");
        //JSONObject에서 key값이 row인 값들만 빼서 jsonArray에 저장한다.
        //row를 키로 갖는 정보들이 찾고자 하는 것들이다.
        List<RoadDto> roadDtoList = new ArrayList<>();
        //도로 정보를 받아올 리스트 생성
        
        RoadDto roadDto = new RoadDto();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            //jsonArray에서 대응되는 값들을 하나씩 가져온다.
            //System.out.println(tempObj);
            roadDto = new RoadDto(tempObj);
            //JSONObject를 전달하면 생성자에서 각 필드를 key값으로 갖는 정보들을 빼서 객체에 저장한다.
            roadDtoList.add(roadDto);
            //리스트에 추가한다.
        }
        return roadDtoList;
        //정보들을 담은 리스트를 반환한다.
    }

    //RoadDto와 같은 순서로 진행된다.
    public List<RoadInfoDto> fromJsonToRoadInfoDto(JSONObject jsonObject) {
//        System.out.println(parsed_Obj);
        JSONObject parsed_Obj = jsonObject.getJSONObject("VolInfo");
        JSONArray jsonArray = parsed_Obj.getJSONArray("row");
        List<RoadInfoDto> roadInfoDtoList = new ArrayList<>();

        RoadInfoDto roadInfoDto = new RoadInfoDto();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tempObj = jsonArray.getJSONObject(i);
            //System.out.println(tempObj);
            roadInfoDto = new RoadInfoDto(tempObj);

            roadInfoDtoList.add(roadInfoDto);
        }
        return roadInfoDtoList;
    }

}
