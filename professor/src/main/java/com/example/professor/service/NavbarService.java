package com.example.professor.service;

import com.example.professor.entity.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
public class NavbarService {

	public void activateNavbarTab(Page tabName, Model model) {
		inactivateAllNavbarTabs(model);
		if (tabName == Page.DASHBOARD) {
			model.addAttribute("dashboardActive", true);
		} else {
			model.addAttribute("quizzesActive", true);
		}
	}

	public void inactivateAllNavbarTabs(Model model) {
		model.addAttribute("dashboardActive", false);
		model.addAttribute("quizzesActive", false);
	}
}
