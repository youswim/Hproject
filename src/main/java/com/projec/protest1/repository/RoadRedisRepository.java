package com.projec.protest1.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projec.protest1.dto.RoadInfoSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RoadRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public List<Object> findRoadInfoDto(RoadInfoSearchDto risDto) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        String risDtoKey = risDto.getRid() + risDto.getDate() + risDto.getTime();
        List<Object> range = list.range(risDtoKey, 0, -1); // 레디스에서 먼저 검색
        if (range != null && !range.isEmpty()) { // 자료가 레디스에 들어있다면
            System.out.println("레디스에서 값 가져옴!!");
            redisTemplate.expire(risDtoKey, 5, TimeUnit.SECONDS);
        }
        return range;
    }

    public void saveRoadInfoDto(RoadInfoSearchDto risDto, String value) {
        String risDtoKey = risDto.getRid() + risDto.getDate() + risDto.getTime();
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(risDtoKey, value); // 레디스에 값 저장
        redisTemplate.expire(risDtoKey, 5, TimeUnit.SECONDS);
    }
}
