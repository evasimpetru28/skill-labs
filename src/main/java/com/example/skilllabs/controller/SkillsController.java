package com.example.skilllabs.controller;

import com.example.skilllabs.entity.Page;
import com.example.skilllabs.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SkillsController {

    final NavbarService navbarService;

    @GetMapping("/skills")
    String getSkillsPage(Model model) {
        navbarService.activateNavbarTab(Page.SKILLS, model);
        return "skills";
    }
}
