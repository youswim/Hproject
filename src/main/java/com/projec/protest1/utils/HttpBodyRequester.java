package com.projec.protest1.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpBodyRequester {
    public String request(String url) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getBody();
    }
}
