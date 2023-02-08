package com.projec.protest1.contorller;

import com.projec.protest1.dto.SignupRequestDto;
import com.projec.protest1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "redirect:/login.html";
    }

    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "redirect:/login.html";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "/pubhtml/signup";
    }
    // 회원 가입 요청 처리

    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/p/";
    }
}