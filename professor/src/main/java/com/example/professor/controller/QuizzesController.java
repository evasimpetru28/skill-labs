package com.example.professor.controller;

import com.example.professor.entity.Page;
import com.example.professor.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class QuizzesController {

	final NavbarService navbarService;
	@GetMapping("/quizzes/{superuserId}")
	public String getQuizzesPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.QUIZZES, model);
		model.addAttribute("superuserId", superuserId);

		return "quizzes";
	}
}
