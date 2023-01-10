package com.projec.protest1.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/p/roads")
    public String roads(){
        return "/pubhtml/roads.html";
    }

    @GetMapping("/p/roadinfo")
    public String roadinfo(){
        return "/pubhtml/roadinfo.html";
    }

    @GetMapping("/p/camera")
    public String camera(){
        return "/pubhtml/camera.html";
    }

    @GetMapping("/a/admin")
    public String admin(){
        return "/admhtml/admin.html";
    }
}
