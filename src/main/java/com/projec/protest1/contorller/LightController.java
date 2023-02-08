package com.projec.protest1.contorller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.projec.protest1.service.LightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LightController {


    private final LightService lightService;
    // 1. 관리자로부터 led 켜짐 요청을 받는다. RequestParam으로 변경할 신호등 선택
    // 2. 요청을 LightService로 넘긴다.

    @PostMapping("/light")
    public void lightChange(@RequestParam String lightNumber) { //lightNumber 검증로직 만들기
        lightService.sendMessage(lightNumber);
    }

    @GetMapping("/led-time")
    public String getLedState() throws UnirestException {
        return lightService.requestLedState();
    }
}
