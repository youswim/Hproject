package com.projec.protest1.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.RoadInfoSearchDto;
import com.projec.protest1.repository.RoadRepository;
import com.projec.protest1.utils.UrlMaker;
import com.projec.protest1.utils.XmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadService {

    private final RoadRepository roadRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UrlMaker urlMaker = new UrlMaker();
    private final XmlParser xp = new XmlParser();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<RoadDto> getRoads() {
        return xp.formXmlToRoadDto(urlMaker.getSpotInfoUrl());
    }

    public List<RoadInfoDto> getRoadInfo(RoadInfoSearchDto risDto) throws JsonProcessingException {

        if (isABeforeOrSameB(getAgoDate(), risDto.getDate())) { // 현재가 더 최신인 경우
            ListOperations<String, Object> list = redisTemplate.opsForList();
            String redisKey = risDto.getRid() + risDto.getDate() + risDto.getTime();
            List<Object> range = list.range(redisKey, 0, -1); // 레디스에서 먼저 검색
            if (range != null && !range.isEmpty()) { // 자료가 레디스에 들어있다면
                System.out.println("레디스에서 값 가져옴!!");
                redisTemplate.expire(redisKey, 5, TimeUnit.SECONDS);
                return range.stream().map(o-> parseJsonToRoadInfoDto((String)o)).collect(Collectors.toList());
            }

            List<RoadInfoDto> result = roadRepository.findRoadEntities(risDto)
                    .stream().map(RoadInfoDto::new).collect(Collectors.toList());
            System.out.println("db에서 값 가져옴!!");
            for (RoadInfoDto dto : result) {
                list.rightPush(redisKey, mapper.writeValueAsString(dto)); // 레디스에 값 저장
                redisTemplate.expire(redisKey, 5, TimeUnit.SECONDS);
                System.out.println("레디스에 값 저장!");
            }
            return result;
        }

        String url = urlMaker.getVolInfoUrl(risDto.getRid().toUpperCase(), risDto.getDate(), risDto.getTime());
        System.out.println("url : " + url);
        return xp.fromXmlToRoadInfoDto(url);
    }

    private String getAgoDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now()
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
