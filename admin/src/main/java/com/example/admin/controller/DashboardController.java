package com.example.admin.controller;

import com.example.admin.entity.Page;
import com.example.admin.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class DashboardController {

	final NavbarService navbarService;

	@GetMapping("/dashboard")
	String getDashboardPage(Model model) {
		navbarService.activateNavbarTab(Page.DASHBOARD, model);
		model.addAttribute("colors", "['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange']");

		return "dashboard";
	}


}
