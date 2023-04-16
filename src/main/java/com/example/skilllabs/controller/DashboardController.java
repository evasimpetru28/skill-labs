package com.example.skilllabs.controller;

import com.example.skilllabs.entity.Page;
import com.example.skilllabs.service.NavbarService;
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

		return "dashboard";
	}


}
