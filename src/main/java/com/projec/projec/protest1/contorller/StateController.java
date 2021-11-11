package com.projec.protest1.contorller;

import com.projec.protest1.dto.StateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {
    String state = "usual";
    StateDto stateDto = new StateDto(state);

    @GetMapping("/state/")
    public StateDto get_state(){
        return stateDto;
    }

    @PostMapping("/state/")
    public String post_state(@RequestBody StateDto sDto){
        String n_state = sDto.getState();
        System.out.println("post내부 진입");
        System.out.println(sDto.getState());
        System.out.println(n_state);

        if(!state.equals(n_state)) {
            state = n_state;
            System.out.println(state);
            stateDto.setState(n_state);
        }
        return "success";
    }
}