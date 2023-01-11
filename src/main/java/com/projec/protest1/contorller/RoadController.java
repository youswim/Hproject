package com.projec.protest1.contorller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.utils.GetJsonObject;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.utils.UrlMaker;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RoadController {
    GetJsonObject getJsonObject = new GetJsonObject();
    UrlMaker urlMaker = new UrlMaker();

    // https://data.seoul.go.kr/dataList/OA-13314/A/1/datasetView.do
    @GetMapping("/api/roads")
    public List<RoadDto> getRoads() throws JsonProcessingException {
        String url = urlMaker.getSpotInfoUrl();
        return getJsonObject.formJsonToRoadDto(getJsonObject.getJsonObject(url));
    }

    // https://data.seoul.go.kr/dataList/OA-13316/A/1/datasetView.do
    @GetMapping("/api/road_info/{rid}/{yyyymmdd}/{time}")
    public List<RoadInfoDto> getRoadInfo(@PathVariable String rid,
                                         @PathVariable int yyyymmdd,
                                         @PathVariable int time) throws JsonProcessingException {
        String url = urlMaker.getVolInfoUrl(rid, yyyymmdd, time);

        return getJsonObject.fromJsonToRoadInfoDto(getJsonObject.getJsonObject(url));
    }


}
