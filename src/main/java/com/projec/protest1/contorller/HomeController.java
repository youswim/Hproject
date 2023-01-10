package com.projec.protest1.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/p")
public class HomeController {

    @GetMapping("/roads")
    public String roads(){
        return "/pubhtml/roads.html";
    }

    @GetMapping("/roadinfo")
    public String roadinfo(){
        return "/pubhtml/roadinfo.html";
    }

    @GetMapping("/camera")
    public String camera(){
        return "/pubhtml/camera.html";
    }


    @GetMapping("/admin")
    public String admin(){
        return "/admhtml/admin.html";
    }
}
