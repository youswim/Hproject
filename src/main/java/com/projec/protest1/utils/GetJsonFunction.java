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
        String url = "http://openapi.seoul.go.kr:8088/" + key + "/xml/"+ str+"/" + start + "/" + end + "/";

        return getJsonObject.formJsonToRoadDto(getJsonObject.getJsonObject(url), str);
    }

    public List<RoadInfoDto> getRoadInfo(String rid, int yyyymmdd, String time) throws JsonProcessingException {
        //RoadInfoDto 리스트를 반환
        String key = "7944415075796f75393267765a5967";
        int start = 1;
        int end = 10;
        String str = "VolInfo";
        String url = "http://openapi.seoul.go.kr:8088/"
                +key+"/xml/"+str+"/"+start+"/"+end+"/"+rid+"/"+yyyymmdd+"/"+time+"/";

        return getJsonObject.fromJsonToRoadInfoDto(getJsonObject.getJsonObject(url), str);

    }
}
