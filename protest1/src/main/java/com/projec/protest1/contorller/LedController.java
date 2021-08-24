package com.projec.protest1.contorller;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class LedController {

    @GetMapping("/led/init")
    public String LedInit(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://192.168.55.2:18080", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }

    @GetMapping("/led/on")
    public String LedOn(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://192.168.55.2:18080/led/on", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }

    @GetMapping("/led/off")
    public String LedOff(){
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://192.168.55.2:18080/led/off", HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return(response);
    }
}
