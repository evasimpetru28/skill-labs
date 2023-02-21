package com.example.skilllabs.controller;

import com.example.skilllabs.entity.Admin;
import com.example.skilllabs.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class RegisterController {

    final AdminService adminService;

    @GetMapping("/register")
    public String getRegisterPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "register";
    }

    @PostMapping("/register/submit")
    public String submitRegister(@ModelAttribute("admin") Admin admin,
                                 @ModelAttribute("confirmPassword") String confirmPassword) {

        if ("".equals(admin.getPhone())) {
            admin.setPhone(null);
        }

        if (!confirmPassword.equals(admin.getPassword())) {
            return "redirect:/register?error=true";
        } else {
            adminService.saveAdmin(admin);
            return "redirect:/login";
        }

    }

}
