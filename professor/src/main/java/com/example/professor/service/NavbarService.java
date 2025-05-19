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
		}
		if (tabName == Page.MY_QUIZZES){
			model.addAttribute("quizzesActive", true);
		}
		if (tabName == Page.PUBLIC_QUIZZES){
			model.addAttribute("publicQuizzesActive", true);
		}
		if (tabName == Page.ALL_SKILLS){
			model.addAttribute("allSkillsActive", true);
		}
		if (tabName == Page.STUDENTS_SELF_EVALUATIONS){
			model.addAttribute("studentsSelfEvaluationsActive", true);
		}
	}

	public void inactivateAllNavbarTabs(Model model) {
		model.addAttribute("dashboardActive", false);
		model.addAttribute("quizzesActive", false);
		model.addAttribute("publicQuizzesActive", false);
		model.addAttribute("allSkillsActive", false);
		model.addAttribute("studentsSelfEvaluationsActive", false);
	}
}
