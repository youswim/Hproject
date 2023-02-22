package com.projec.protest1.utils;

import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExternalApiRequester {

    private final UrlMaker urlMaker = new UrlMaker();
    private final HttpBodyRequester httpBodyRequester = new HttpBodyRequester();
    private final XmlParser xmlParser = new XmlParser();

    public List<RoadSpotInfo> requestRoadSpotInfos() {
        String roadSpotInfoUrl = urlMaker.makeRoadSpotInfoUrl(); // 요청할 url 가져오기
        String roadListHttpBody = httpBodyRequester.request(roadSpotInfoUrl); // url에 요청하고 body얻음
        List<RoadSpotInfo> roadSpotInfos = null;
        try {
            roadSpotInfos = xmlParser.xmlStringToRoadDto(roadListHttpBody); // 얻은 body를 dtoList로 파싱
        } catch (IllegalStateException e) {
            log.warn(roadSpotInfoUrl + " = " + e.getMessage());
        }
        return roadSpotInfos;
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
