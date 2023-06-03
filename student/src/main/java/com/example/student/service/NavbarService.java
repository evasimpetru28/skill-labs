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
			case ALL_SKILLS:
				model.addAttribute("allSkillsActive", true);
				break;
			case ASSIGNMENTS:
				model.addAttribute("assignmentsActive", true);
				break;
		}
	}

	public void inactivateAllNavbarTabs(Model model) {
		model.addAttribute("mySkillsActive", false);
		model.addAttribute("allSkillsActive", false);
		model.addAttribute("assignmentsActive", false);
	}
}
