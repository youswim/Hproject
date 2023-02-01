package com.projec.protest1.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projec.protest1.RedisConfig;
import com.projec.protest1.dto.RoadInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisTest {

    @Test
    @DisplayName("list 테스트")
    public void list() throws Exception{
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate = ac.getBean("redisTemplate", RedisTemplate.class);

        ListOperations<String, Object> list = redisTemplate.opsForList();
        String key = "userList";

        List<Object> expect = new ArrayList<>();
        expect.add(new RoadInfoDto(1,1,1));
        expect.add(new RoadInfoDto(2,2,2));
        expect.add(new RoadInfoDto(3,3,3));

        //when
        ObjectMapper objectMapper = new ObjectMapper();
        list.rightPush("userList",objectMapper.writeValueAsString(expect.get(0)));
        list.rightPush("userList",objectMapper.writeValueAsString(expect.get(1)));
        list.rightPush("userList",objectMapper.writeValueAsString(expect.get(2)));

        List<Object> range = list.range(key, 0, 2);
        List<Object> acutal = new ArrayList<>();
        for (Object o : range) {
            acutal.add(objectMapper.readValue((String)o, RoadInfoDto.class));

        }
        System.out.println(acutal);

        //then
        assertThat(acutal).usingRecursiveComparison().isEqualTo(expect);
    }
}
