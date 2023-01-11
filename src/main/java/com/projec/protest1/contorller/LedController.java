package com.projec.protest1.contorller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class LedController {

    // 1. 관리자로부터 led 켜짐 요청을 받는다
    // 2. 켜짐 요청을 받은 spring 컨트롤러가 라즈베리파이 led 서버에 요청을 보낸다
    // 3. 요청을 전달받은 라즈베리파이는 led 켜짐을 수행하고 문자열을 반환한다.
    // 4. 라즈베리파이가 반환한 문자열을 spring 컨트롤러가 받아서 클라이언트에 전달한다.

    @GetMapping("/led/1/on")
    public String Led_1_On() {
        return getMessageFromLed(makeUrl("led1"));
    }

    @GetMapping("/led/2/on")
    public String Led_2_On() {
        return getMessageFromLed(makeUrl("led2"));
    }

    String makeUrl(String uri) {
        return "http://192.168.55.2:18080/" + uri + "/on";
    }

    String getMessageFromLed(String url) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String responseMessage = responseEntity.getBody();
        log.info("status={}, responseMessage={}", status, responseMessage);
        return responseMessage;
    }
}
