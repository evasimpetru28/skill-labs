package com.example.skilllabs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class RegisterController {

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

}
