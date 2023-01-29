package com.projec.protest1.contorller;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedTimeController {

    static final String LIGHT_STATE_URL = "http://192.168.45.36:18080/ledtime";

    private String request(String url) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        return Unirest.get(url).asString().getBody();
    }

    @GetMapping("/ledtime")
    public String getLedTime() throws UnirestException {
        // {"light_number":1,"time":3} 이 형태가 만들어져서 내려간다.
        return request(LIGHT_STATE_URL);
    }
}
