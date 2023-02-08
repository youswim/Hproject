package com.projec.protest1.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/p/roads")
    public String inquiryRoadList(){
        return "/pubhtml/roads.html";
    }

    @GetMapping("/p/road-info")
    public String inquiryRoadInfos(){
        return "/pubhtml/roadinfo.html";
    }

    @GetMapping("/p/camera")
    public String inquiryVideo(){
        return "/pubhtml/camera.html";
    }

    @GetMapping("/a/admin")
    public String admin(){
        return "/admhtml/admin.html";
    }
}
