package com.projec.protest1.contorller;

import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.utils.UrlRequester;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.utils.UrlMaker;
import com.projec.protest1.utils.XmlParser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoadController {

    UrlMaker urlMaker = new UrlMaker();
    XmlParser xp = new XmlParser();


    // https://data.seoul.go.kr/dataList/OA-13314/A/1/datasetView.do
    @GetMapping("/api/roads")
    public List<RoadDto> getRoads() {
        String url = urlMaker.getSpotInfoUrl();
        return xp.formJsonToRoadDto(url);
    }

    // https://data.seoul.go.kr/dataList/OA-13316/A/1/datasetView.do
    @GetMapping("/api/road_info/{rid}/{yyyymmdd}/{time}")
    public List<RoadInfoDto> getRoadInfo(@PathVariable String rid,
                                         @PathVariable int yyyymmdd,
                                         @PathVariable int time) {
        String url = urlMaker.getVolInfoUrl(rid, yyyymmdd, time);
        return xp.fromJsonToRoadInfoDto(url);
    }
}
