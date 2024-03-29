package com.projec.protest1.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.RoadInfoSearchDto;
import com.projec.protest1.exception.ApiErrorCodeException;
import com.projec.protest1.repository.RoadRedisRepository;
import com.projec.protest1.repository.RoadRepository;
import com.projec.protest1.repository.RoadSpotInfoRepository;
import com.projec.protest1.utils.ExternalApiRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoadService {

    private final RoadRepository roadRepository;
    private final RoadRedisRepository roadRedisRepository;
    private final RoadSpotInfoRepository roadSpotInfoRepository;
    private final ExternalApiRequester apiRequester = new ExternalApiRequester();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<RoadSpotInfo> findRoadSpotInfos() {
        List<RoadSpotInfo> roadSpotInfos = new ArrayList<>();
        roadSpotInfoRepository.findAll().forEach(roadSpotInfos::add);
        return roadSpotInfos;
    }

    public List<RoadInfoDto> getRoadInfo(RoadInfoSearchDto risDto) throws ApiErrorCodeException {

        if (isABeforeOrSameB(getAgoDate(LocalDate.now()), risDto.getDate())) { // 5년전보다 입력받은 날짜가 최신이라면
            List<RoadInfoDto> infoFromLocalResult = findInfoFromLocal(risDto);
            if (infoFromLocalResult.size() != 0) {
                return infoFromLocalResult;
            }
        }
        return apiRequester.requestRoadVolInfo(risDto.getRid().toUpperCase(), risDto.getDate(), risDto.getTime());
    }

    private List<RoadInfoDto> findInfoFromLocal(RoadInfoSearchDto risDto) {
        List<Object> redisFindResults = roadRedisRepository.findRoadInfoDto(risDto); // 레디스에서 먼저 찾아보기
        if (!redisFindResults.isEmpty()) { // 레디스에 값이 들어있다면,
            return redisFindResults.stream()
                    .map(o -> parseJsonToRoadInfoDto((String) o))
                    .collect(Collectors.toList());
        }

        List<RoadInfoDto> result = roadRepository.findRoadEntities(risDto).stream()
                .map(RoadInfoDto::new)
                .collect(Collectors.toList()); // db에서 찾아옴
        System.out.println("db에서 값 가져옴!!");

        for (RoadInfoDto dto : result) {
            try {
                roadRedisRepository.saveRoadInfoDto(risDto, mapper.writeValueAsString(dto));
            } catch (JsonProcessingException e) {
                System.out.println("RoadService.findInfoFromLocal");
                log.warn(e.getMessage());
            }
            System.out.println("레디스에 값 저장!");
        }
        return result;
    }

    private String getAgoDate(LocalDate localDate) { // minusYears 만큼 이전의 날짜를 반환함.
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return localDate
                .minusYears(5)
                .format(dateTimeFormatter);
    }

    private boolean isABeforeOrSameB(String a, String b) {
        return Integer.parseInt(a) <= Integer.parseInt(b);
    }

    private RoadInfoDto parseJsonToRoadInfoDto(String str) {
        RoadInfoDto dto = new RoadInfoDto();
        try {
            dto = mapper.readValue(str, RoadInfoDto.class);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return dto;
    }
}
