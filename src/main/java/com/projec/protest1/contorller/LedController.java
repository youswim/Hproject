package com.projec.protest1.contorller;


import com.projec.protest1.service.LightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LedController {

    private final LightService lightService;
    // 1. 관리자로부터 led 켜짐 요청을 받는다
    // 2. 켜짐 요청을 받은 spring 컨트롤러가 라즈베리파이 led 서버에 요청을 보낸다
    // 3. 요청을 전달받은 라즈베리파이는 led 켜짐을 수행하고 문자열을 반환한다.
    // 4. 라즈베리파이가 반환한 문자열을 spring 컨트롤러가 받아서 클라이언트에 전달한다.

    @PostMapping("/light")
    public void lightChange(@RequestParam String lightNumber) { //lightNumber 검증로직 만들기
        lightService.sendMessage(lightNumber);
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
