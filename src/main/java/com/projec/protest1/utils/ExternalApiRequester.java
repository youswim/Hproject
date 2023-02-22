package com.projec.protest1.utils;

import com.projec.protest1.dto.RoadSpotInfoDto;
import com.projec.protest1.dto.RoadInfoDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExternalApiRequester {

    private final UrlMaker urlMaker = new UrlMaker();
    private final HttpBodyRequester httpBodyRequester = new HttpBodyRequester();
    private final XmlParser xmlParser = new XmlParser();

    public List<RoadSpotInfoDto> requestRoadList() {
        String roadListUrl = urlMaker.makeRoadListUrl(); // 요청할 url 가져오기
        String roadListHttpBody = httpBodyRequester.request(roadListUrl); // url에 요청하고 body얻음
        List<RoadSpotInfoDto> roadSpotInfoDtos = null;
        try {
            roadSpotInfoDtos = xmlParser.xmlStringToRoadDto(roadListHttpBody); // 얻은 body를 dtoList로 파싱
        } catch (IllegalStateException e) {
            log.warn(roadListUrl + " = " + e.getMessage());
        }
        return roadSpotInfoDtos;
    }

    public List<RoadInfoDto> requestRoadVolInfo(String rid, String date, Integer time) {
        String volInfoUrl = urlMaker.makeVolInfoUrl(rid, date, time);
        String roadVolInfoHttpBody = httpBodyRequester.request(volInfoUrl);
        List<RoadInfoDto> roadInfoDtos = null;
        try {
            roadInfoDtos = xmlParser.xmlStringToRoadInfoDto(roadVolInfoHttpBody);
        } catch (IllegalStateException e) {
            log.warn(volInfoUrl + " = " + e.getMessage());
        }
        return roadInfoDtos;
    }
}
