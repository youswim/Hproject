package com.projec.protest1.contorller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.projec.protest1.domain.RoadInfoDto;
import com.projec.protest1.utils.GetJsonObject;
import com.projec.protest1.domain.RoadDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RoadController {

    GetJsonObject getJsonObject = new GetJsonObject();


    @GetMapping("/api/roads")
    public List<RoadDto> getRoads() throws JsonProcessingException {
        String key = "7944415075796f75393267765a5967";
        int start = 1;
        int end = 169;
        String str = "SpotInfo";
        String url = "http://openapi.seoul.go.kr:8088/" + key + "/xml/" + str + "/" + start + "/" + end + "/";


        return getJsonObject.formJsonToRoadDto(getJsonObject.getJsonObject(url));
        //roadDto 리스트를 클라이언트에 전달
    }

    @GetMapping("/api/road_info/{rid}/{yyyymmdd}/{time}")
    public List<RoadInfoDto> getRoadInfo(@PathVariable String rid,
                                         @PathVariable int yyyymmdd,
                                         @PathVariable int time) throws JsonProcessingException {
        String key = "7944415075796f75393267765a5967";
        int start = 1;
        int end = 10;
        String str = "VolInfo";
        String url = "http://openapi.seoul.go.kr:8088/"
                + key + "/xml/" + str + "/" + start + "/" + end + "/" + rid + "/" + yyyymmdd + "/" + time + "/";

        return getJsonObject.fromJsonToRoadInfoDto(getJsonObject.getJsonObject(url));
        //roadInfoDto 리스트를 클라이언트에 전달

    }
}
