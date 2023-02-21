package com.example.skilllabs.controller;

import com.example.skilllabs.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    final AdminService adminService;

    @GetMapping("/profile/{adminId}")
    String getProfilePage(@PathVariable String adminId, Model model) {
        var admin = adminService.getAdminById(adminId);

        model.addAttribute("admin", admin);
        return "profile";
    }

}
