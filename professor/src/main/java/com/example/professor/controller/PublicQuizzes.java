package com.example.professor.controller;

import com.example.professor.entity.Page;
import com.example.professor.service.NavbarService;
import com.example.professor.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PublicQuizzes {

	final QuizService quizService;
	final NavbarService navbarService;

	@GetMapping("/public-quizzes/{superuserId}")
	public String getPublicQuizzesPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.PUBLIC_QUIZZES, model);
		var quizzList = quizService.getPublicQuizzes();

		model.addAttribute("quizList", quizzList);
		model.addAttribute("noQuizzes", quizzList.isEmpty());
		model.addAttribute("superuserId", superuserId);

		return "public-quizzes";
	}

}
