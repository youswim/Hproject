package com.projec.protest1.contorller;

import com.projec.protest1.dto.LedTimeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedTimeController {
    String rec_LedTime = "1";
    LedTimeDto ledTimeDto = new LedTimeDto(rec_LedTime);
    String new_LedTime;

    @GetMapping("/ledtime/")
    public LedTimeDto getLedTime(){
        return ledTimeDto;
    }

    @PostMapping("/ledtime/")
    public String postLedTime(@RequestBody LedTimeDto new_LedTimeDto){
        new_LedTime = new_LedTimeDto.getLedTime();
        System.out.println("postLedTime 진입");
        System.out.println(ledTimeDto.getLedTime());
        System.out.println(new_LedTime);

        if(!rec_LedTime.equals(new_LedTime)){
            rec_LedTime = new_LedTime;
            System.out.println(rec_LedTime);
            ledTimeDto.setLedTime(rec_LedTime);
        }
        return "success";
    }
}
