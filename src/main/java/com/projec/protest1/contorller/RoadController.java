package com.projec.protest1.contorller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projec.protest1.domain.RoadAll;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.repository.RoadRepository;
import com.projec.protest1.utils.UrlMaker;
import com.projec.protest1.utils.XmlParser;
import com.projec.protest1.dto.RoadInfoSearchDto;
import com.projec.protest1.validation.RoadInfoValidator;
import com.projec.protest1.exceptionapi.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoadController {
    private final RoadInfoValidator roadInfoValidator;
    private final MessageSource messageSource;
    private final RoadRepository roadRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    ObjectMapper mapper = new ObjectMapper();

    UrlMaker urlMaker = new UrlMaker();
    XmlParser xp = new XmlParser();

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(roadInfoValidator);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationResult handleBindException(BindException bindException, Locale locale) {
//        System.out.println("RoadController.handleBindException");
        return ValidationResult.create(bindException, messageSource, locale);
    }

    // https://data.seoul.go.kr/dataList/OA-13314/A/1/datasetView.do
    @GetMapping("/api/roads")
    public List<RoadDto> getRoads() {
        String url = urlMaker.getSpotInfoUrl();
        return xp.formXmlToRoadDto(url);
    }

    // https://data.seoul.go.kr/dataList/OA-13316/A/1/datasetView.do
    @GetMapping("/api/road_info")
    public List<RoadInfoDto> getRoadInfo(@Validated @ModelAttribute RoadInfoSearchDto roadInfoSearchDto, BindingResult bindingResult) throws BindException, JsonProcessingException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String agoDate = LocalDate.now()
                .minusYears(5)
                .format(dateTimeFormatter);

        if (Integer.parseInt(agoDate) <= Integer.parseInt(roadInfoSearchDto.getDate())) { // 현재가 더 최신인 경우
            ListOperations<String, Object> list = redisTemplate.opsForList();
            String redisKey = roadInfoSearchDto.getRid() + roadInfoSearchDto.getDate() + roadInfoSearchDto.getTime();
            List<Object> range = list.range(redisKey, 0, -1); // 레디스에서 먼저 검색
            if (range != null && !range.isEmpty()) { // 자료가 레디스에 들어있다면
                System.out.println("레디스에서 값 가져옴!!");
                return range.stream().map(o -> {
                    try {
                        return mapper.readValue((String) o, RoadInfoDto.class); // 해당 값 리턴
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
            }

            List<RoadInfoDto> result = roadRepository.findRoadEntities(roadInfoSearchDto.getRid(), roadInfoSearchDto.getDate(), roadInfoSearchDto.getTime())
                    .stream().map(RoadInfoDto::new).collect(Collectors.toList());
            System.out.println("db에서 값 가져옴!!");
            for (RoadInfoDto dto : result) {
                list.rightPush(redisKey, mapper.writeValueAsString(dto)); // 레디스에 값 저장
                System.out.println("레디스에 값 저장!");
            }
            return result;
        }

        String url = urlMaker.getVolInfoUrl(roadInfoSearchDto.getRid().toUpperCase(), roadInfoSearchDto.getDate(), roadInfoSearchDto.getTime());
        System.out.println("url : " + url);
        return xp.fromXmlToRoadInfoDto(url);
    }
}
