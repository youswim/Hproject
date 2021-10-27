package com.projec.protest1.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/p/")
    public String index(){
        return"/pubhtml/index.html";
    }

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

    @GetMapping("/a/light")
    public String light(){
        return "/admhtml/light.html";
    }

    @GetMapping("/a/admin")
    public String admin(){
        return "/admhtml/admin.html";
    }
}
