package com.example.professor.controller;

import com.example.professor.entity.Page;
import com.example.professor.service.NavbarService;
import com.example.professor.service.SuperuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class DashboardController {

	final NavbarService navbarService;
	final SuperuserService superuserService;

	@GetMapping("/dashboard/{superuserId}")
	String getDashboardPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.DASHBOARD, model);
		var superuser = superuserService.getSuperuserById(superuserId);
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		return "dashboard";
	}


}
