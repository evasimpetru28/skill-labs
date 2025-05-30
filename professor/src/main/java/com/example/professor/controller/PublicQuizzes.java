package com.example.professor.controller;

import com.example.professor.entity.Page;
import com.example.professor.service.NavbarService;
import com.example.professor.service.QuestionService;
import com.example.professor.service.QuizService;
import com.example.professor.service.SuperuserService;
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
	final QuestionService questionService;
	final SuperuserService superuserService;

	@GetMapping("/public-quizzes/{superuserId}")
	public String getPublicQuizzesPage(Model model, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.PUBLIC_QUIZZES, model);
		var quizzList = quizService.getPublicQuizzes();
		var superuser = superuserService.getSuperuserById(superuserId);
		model.addAttribute("quizList", quizzList);
		model.addAttribute("noQuizzes", quizzList.isEmpty());
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		model.addAttribute("superuser", superuserService.getSuperuserById(superuserId).getName());

		return "public-quizzes";
	}

	@GetMapping("/quiz-preview/{quizId}/{superuserId}")
	public String getQuizPreviewPage(Model model, @PathVariable String quizId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.PUBLIC_QUIZZES, model);

		var quiz = quizService.getQuizDetailsById(quizId);
		var superuser = superuserService.getSuperuserById(superuserId);
		model.addAttribute("quiz", quiz);
		model.addAttribute("questionMap", questionService.getQuestionMap(quizId));
		model.addAttribute("superuserId", quiz.getSuperuserId());
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));

		return "quiz-preview";
	}

}
