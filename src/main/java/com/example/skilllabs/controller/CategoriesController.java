package com.example.skilllabs.controller;

import com.example.skilllabs.entity.Page;
import com.example.skilllabs.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CategoriesController {

    final NavbarService navbarService;

    @GetMapping("/categories")
    String getCategoriesPage(Model model) {
        navbarService.activateNavbarTab(Page.CATEGORIES, model);
        return "categories";
    }
}
