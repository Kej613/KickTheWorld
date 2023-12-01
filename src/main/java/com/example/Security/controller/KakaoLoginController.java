//package com.example.Security.controller;
//
//import com.example.Security.service.KakaoLoginService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@RequiredArgsConstructor
//@Controller
//public class KakaoLoginController {
//    private final KakaoLoginService kakaoLoginService;
//
//    @GetMapping("/members/login")
//    public String login(Model model) {
//        model.addAttribute("kakaoUrl", kakaoLoginService.getKakaoLogin());
//        return "member/LoginForm";
//    }
//
//}
