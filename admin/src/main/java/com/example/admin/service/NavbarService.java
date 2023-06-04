package com.example.admin.service;

import com.example.admin.entity.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class NavbarService {

	public void activateNavbarTab(Page tabName, Model model) {
		inactivateAllNavbarTabs(model);
		switch (tabName) {
			case DASHBOARD -> model.addAttribute("dashboardActive", true);
			case USER_MANAGEMENT -> model.addAttribute("userManagementActive", true);
			case SKILLS -> model.addAttribute("skillsActive", true);
			case CATEGORIES -> model.addAttribute("categoriesActive", true);
		}
	}

	public void inactivateAllNavbarTabs(Model model) {
		model.addAttribute("dashboardActive", false);
		model.addAttribute("userManagementActive", false);
		model.addAttribute("skillsActive", false);
		model.addAttribute("categoriesActive", false);
	}
}
