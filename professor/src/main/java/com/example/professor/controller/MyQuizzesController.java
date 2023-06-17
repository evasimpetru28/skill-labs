package com.example.professor.controller;

import com.example.professor.entity.Option;
import com.example.professor.entity.Page;
import com.example.professor.entity.Question;
import com.example.professor.entity.Quiz;
import com.example.professor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MyQuizzesController {

	final QuizService quizService;
	final NavbarService navbarService;
	final OptionService optionService;
	final QuestionService questionService;

	@GetMapping("/quizzes/assigned/{superuserId}")
	public String getAssignedQuizzesPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		var assignedQuizzesBySuperuserId = quizService.getAssignedQuizzesBySuperuserId(superuserId);

		model.addAttribute("superuserId", superuserId);
		model.addAttribute("assigned", true);
		model.addAttribute("expired", false);
		model.addAttribute("drafted", false);
		model.addAttribute("quizList", assignedQuizzesBySuperuserId);
		model.addAttribute("noQuizzes", assignedQuizzesBySuperuserId.isEmpty());

		return "my-quizzes";
	}

	@GetMapping("/quizzes/expired/{superuserId}")
	public String getExpiredQuizzesPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		var expiredQuizzesBySuperuserId = quizService.getExpiredQuizzesBySuperuserId(superuserId);

		model.addAttribute("superuserId", superuserId);
		model.addAttribute("assigned", false);
		model.addAttribute("expired", true);
		model.addAttribute("drafted", false);
		model.addAttribute("quizList", expiredQuizzesBySuperuserId);
		model.addAttribute("noQuizzes", expiredQuizzesBySuperuserId.isEmpty());

		return "my-quizzes";
	}

	@GetMapping("/quizzes/drafted/{superuserId}")
	public String getDraftedQuizzesPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		var draftedQuizzesBySuperuserId = quizService.getDraftedQuizzesBySuperuserId(superuserId);
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("assigned", false);
		model.addAttribute("expired", false);
		model.addAttribute("drafted", true);
		model.addAttribute("quizList", draftedQuizzesBySuperuserId);
		model.addAttribute("noQuizzes", draftedQuizzesBySuperuserId.isEmpty());

		return "my-quizzes";
	}

	@PostMapping("/add-quiz/{superuserId}")
	public String addQuiz(@PathVariable String superuserId) {
		var quiz = new Quiz();
		quiz.setSuperuserId(superuserId);
		quiz.setName("Untitled");
		quiz.setStatus("PUBLIC");
		quiz.setIsReady(false);
		quizService.saveQuiz(quiz);

		addQuestionAndOption(quiz.getId());
		return "redirect:/new-quiz/" + quiz.getId();
	}

	@PostMapping("/update-quiz-name/{quizId}/{quizName}")
	public void updateQuizName(@PathVariable String quizId, @PathVariable String quizName) {
		var quiz = quizService.getQuizById(quizId);
		quiz.setName(quizName);
		quizService.saveQuiz(quiz);
	}

	@PostMapping("/update-quiz-descr/{quizId}/{description}")
	public void updateQuizDescription(@PathVariable String quizId, @PathVariable String description) {
		var quiz = quizService.getQuizById(quizId);
		quiz.setDescription(description);
		quizService.saveQuiz(quiz);
	}

	@GetMapping("/new-quiz/{quizId}")
	public String getCreateQuizPage(Model model, @PathVariable String quizId) {
		//TODO: Get logged user id from session
		var quiz = quizService.getQuizById(quizId);
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		model.addAttribute("superuserId", quiz.getSuperuserId());
		model.addAttribute("quizId", quizId);
		model.addAttribute("quiz", quiz);
		model.addAttribute("questionMap", questionService.getQuestionMap(quizId));
		return "create-quiz";
	}

	@PostMapping("/add-question/{quizId}")
	public String addQuestion(@PathVariable String quizId) {
		addQuestionAndOption(quizId);
		return "redirect:/new-quiz/" + quizId;
	}

	private void addQuestionAndOption(String quizId) {
		var question = new Question();
		question.setQuizId(quizId);
		question.setQuestion("Untitled Question");
		questionService.saveQuestion(question);

		var option = new Option();
		option.setQuestionId(question.getId());
		option.setOptionText("Option 1");
		option.setIsCorrect(false);
		optionService.saveOption(option);
	}

	@PostMapping("/update-question/{questionId}/{questionName}")
	public void updateQuestion(@PathVariable String questionId, @PathVariable String questionName) {
		var question = questionService.getQuestionById(questionId);
		question.setQuestion(questionName);
		questionService.saveQuestion(question);
	}

	@PostMapping("/delete-question/{questionId}/{quizId}")
	public String deleteQuestion(@PathVariable String questionId, @PathVariable String quizId) {
		optionService.deleteOptionsOfQuestion(questionId);
		questionService.deleteQuestionById(questionId);
		return "redirect:/new-quiz/" + quizId;
	}

	@PostMapping("/make-expired/{quizId}")
	public String makeExpiredQuiz(@PathVariable String quizId) {
		var quiz = quizService.getQuizById(quizId);
		quiz.setStatus("EXPIRED");
		quizService.saveQuiz(quiz);
		return "redirect:/new-quiz/" + quizId;
	}

	@PostMapping("/add-option/{questionId}/{quizId}")
	public String addOption(@PathVariable String questionId, @PathVariable String quizId) {
		var optionNumber = optionService.getOptionNumberOfQuestion(questionId);

		var option = new Option();
		option.setQuestionId(questionId);
		option.setOptionText("Option " + (optionNumber + 1));
		option.setIsCorrect(false);
		optionService.saveOption(option);
		return "redirect:/new-quiz/" + quizId;
	}

	@PostMapping("/update-option-text/{optionId}/{optionText}")
	public void updateOptionText(@PathVariable String optionId, @PathVariable String optionText) {
		var option = optionService.getOptionById(optionId);
		option.setOptionText(optionText);
		optionService.saveOption(option);
	}

	@PostMapping("/update-option-checked/{optionId}/{isCorrect}")
	public void updateOptionChecked(@PathVariable String optionId, @PathVariable Boolean isCorrect) {
		var option = optionService.getOptionById(optionId);
		option.setIsCorrect(isCorrect);
		optionService.saveOption(option);
	}

	@PostMapping("/delete-option/{optionId}/{quizId}")
	public String deleteOption(@PathVariable String optionId, @PathVariable String quizId) {
		optionService.deleteOptionById(optionId);
		return "redirect:/new-quiz/" + quizId;

	}

	@PostMapping("/next/{quizId}")
	public String sendQuiz(@PathVariable String quizId) {
		return "redirect:/assign-students/" + quizId;
	}

	@PostMapping("/change-quiz-status/{quizId}/{status}")
	public void changeQuizStatus(@PathVariable String quizId, @PathVariable String status) {
		var quiz = quizService.getQuizById(quizId);
		quiz.setStatus(status);
		quizService.saveQuiz(quiz);
	}

	@PostMapping("edit-drafted/{quizId}")
	public String editDraftedQuiz(@PathVariable String quizId) {
		return "redirect:/new-quiz/" + quizId;
	}

	@PostMapping("edit-assigned/{quizId}")
	public String editAsignedQuiz(@PathVariable String quizId) {
		return "redirect:/assign-students/" + quizId;
	}

}
