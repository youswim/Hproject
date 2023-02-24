package com.projec.protest1.utils;

import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.exception.ApiErrorCodeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExternalApiRequester {

    private final UrlMaker urlMaker = new UrlMaker();
    private final HttpBodyRequester httpBodyRequester = new HttpBodyRequester();
    private final XmlParser xmlParser = new XmlParser();

    public List<RoadSpotInfo> requestRoadSpotInfos() throws ApiErrorCodeException {
        String spotInfoUrl = urlMaker.makeRoadSpotInfoUrl(); // 요청할 url 가져오기
        String roadListHttpBody = httpBodyRequester.request(spotInfoUrl); // url에 요청하고 body얻음
        return xmlParser.xmlStringToRoadDto(roadListHttpBody, spotInfoUrl); // 얻은 body를 dtoList로 파싱
    }

    public List<RoadInfoDto> requestRoadVolInfo(String rid, String date, Integer time) throws ApiErrorCodeException {
        String volInfoUrl = urlMaker.makeVolInfoUrl(rid, date, time);
        String roadVolInfoHttpBody = httpBodyRequester.request(volInfoUrl);
        return xmlParser.xmlStringToRoadInfoDto(roadVolInfoHttpBody, volInfoUrl);
    }
}
