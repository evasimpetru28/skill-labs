package com.example.professor.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.professor.entity.Page;
import com.example.professor.model.CategoryWithSkills;
import com.example.professor.service.CategoryService;
import com.example.professor.service.NavbarService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class AllSkillsController {

	final NavbarService navbarService;
    final CategoryService categoryService;

    @GetMapping("/all-skills/{superuserId}")
	public String getAllSkillsPage(Model model, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		
        List<CategoryWithSkills> categories = categoryService.getAllCategoriesAndSkills();

        model.addAttribute("noSkills", categories.isEmpty());
        model.addAttribute("categories", categories);
        model.addAttribute("superuserId", superuserId);
		return "all-skills";
	}
}
