package com.example.skilllabs.controller;

import com.example.skilllabs.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LoginController {

    final AdminService adminService;

    @GetMapping("/")
    String getStartPage() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    String getLoginPage(@RequestParam(required = false) final boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login/submit")
    String submitLogin(@RequestParam String email, @RequestParam String password) {
        var admin = adminService.getAdminByCredentials(email, password);

        if (admin.isPresent()) {
            return "redirect:/profile/" + admin.get().getId();
        }
        else {
            return "redirect:/login?error=true";
        }
    }

}
