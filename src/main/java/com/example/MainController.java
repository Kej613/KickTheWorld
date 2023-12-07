package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
//
    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage_test";
    }

//    @GetMapping("/")
//    public String mainPage() {
//        return "mainPage";
//    }

    @GetMapping("/")
    public String mainPage() {
        return "mainPage_test";
    }

}