package com.example.student.service;

import com.example.student.entity.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class NavbarService {

	public void activateNavbarTab(Page tabName, Model model) {
		inactivateAllNavbarTabs(model);
		switch (tabName) {
			case MY_SKILLS:
				model.addAttribute("mySkillsActive", true);
				break;
			case USER_MANAGEMENT:
				model.addAttribute("userManagementActive", true);
				break;
			case ALL_SKILLS:
				model.addAttribute("allSkillsActive", true);
				break;
			case CATEGORIES:
				model.addAttribute("categoriesActive", true);
				break;
		}
	}

	public void inactivateAllNavbarTabs(Model model) {
		model.addAttribute("dashboardActive", false);
		model.addAttribute("userManagementActive", false);
		model.addAttribute("skillsActive", false);
		model.addAttribute("categoriesActive", false);
	}
}
