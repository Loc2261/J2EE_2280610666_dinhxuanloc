package com.example.bai2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("message", "Hello from Controller!");
        model.addAttribute("home.title", "Trang chủ");
        return "index";
    }
}
