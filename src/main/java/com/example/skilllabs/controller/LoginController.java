package com.example.skilllabs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/")
    String getStartPage() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    String getLoginPage() {
        return "login";
    }

}
