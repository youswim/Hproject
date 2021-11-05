package com.projec.protest1.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.projec.protest1.domain.RoadDto;
import com.projec.protest1.domain.RoadInfoDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class GetJsonFunction {

    GetJsonObject getJsonObject = new GetJsonObject();

    public List<RoadDto> getRoads() throws JsonProcessingException {
        //RoadDto 리스트를 반환
        String key = "7944415075796f75393267765a5967";
        int start = 1;
        int end = 169;
        String str = "SpotInfo";
        String url = "http://openapi.seoul.go.kr:8088/" + key + "/xml/" + str + "/" + start + "/" + end + "/";
        //api에 GET방식으로 요청하기 위한 특정 주소를 url에 담아서 함수에 전달한다.

        return getJsonObject.formJsonToRoadDto(getJsonObject.getJsonObject(url));
        //RoadDto 리스트를 반환. 즉, 도로 정보가 담긴 객체 리스트가 반환됨
    }

    public List<RoadInfoDto> getRoadInfo(String rid, int yyyymmdd, String time) throws JsonProcessingException {
        //도로 id, 날짜, 시간을 인자로 받는다.
        //RoadInfoDto 리스트를 반환
        String key = "7944415075796f75393267765a5967";
        int start = 1;
        int end = 10;
        String str = "VolInfo";
        String url = "http://openapi.seoul.go.kr:8088/"
                + key + "/xml/" + str + "/" + start + "/" + end + "/" + rid + "/" + yyyymmdd + "/" + time + "/";

        return getJsonObject.fromJsonToRoadInfoDto(getJsonObject.getJsonObject(url));
        //RoadInfoDto를 담은 리스트를 반환한다.

    }
}
