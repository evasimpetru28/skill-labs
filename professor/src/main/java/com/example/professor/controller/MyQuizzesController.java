package com.example.professor.controller;

import com.example.professor.entity.Option;
import com.example.professor.entity.Page;
import com.example.professor.entity.Question;
import com.example.professor.entity.Quiz;
import com.example.professor.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyQuizzesController {

	final QuizService quizService;
	final NavbarService navbarService;
	final OptionService optionService;
	final QuestionService questionService;
	final ResponseService responseService;
	final AssignmentService assignmentService;
	final StudentService studentService;

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
		quiz.setStatus("PRIVATE");
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
		model.addAttribute("isPublic", "PUBLIC".equals(quiz.getStatus()));
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

	@PostMapping("/make-expired/{quizId}/{superuserId}")
	public String makeExpiredQuiz(@PathVariable String quizId, @PathVariable String superuserId) {
		System.out.println("------------EXPIRED--------------");
		var quiz = quizService.getQuizById(quizId);
		quiz.setStatus("EXPIRED");
		quizService.saveQuiz(quiz);
		return "redirect:/quizzes/expired/" + superuserId;
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

	@PostMapping("delete-quiz/{quizId}/{quizType}")
	public String deleteQuiz(@PathVariable String quizId, @PathVariable String quizType) {
		var superuserId = quizService.getQuizById(quizId).getSuperuserId();
		var questions = questionService.getAllQuestionsByQuizId(quizId);
		questions.forEach(question -> {
			log.info("Delete all responses for question with id {}", question.getId());
			responseService.deleteAllResponsesByQuestionId(question.getId());
			log.info("Delete all options for question with id {}", question.getId());
			optionService.deleteOptionsOfQuestion(question.getId());
			log.info("Delete question with id {}", question.getId());
			questionService.deleteQuestionById(question.getId());
		});

		log.info("Delete assignments for quiz with id {}", quizId);
		assignmentService.deleteAllAssignmentsForQuizId(quizId);
		log.info("Delete quiz with id {}", quizId);
		quizService.deleteQuiz(quizId);

		return "redirect:/quizzes/" + quizType + "/" + superuserId;
	}

	@PostMapping("/ready-quiz/{quizId}/{superuserId}")
	public String readyQuiz(@PathVariable String quizId, @PathVariable String superuserId) {
		var quiz = quizService.getQuizById(quizId);
		quiz.setIsReady(true);
		quizService.saveQuiz(quiz);
		return "redirect:/quizzes/assigned/" + superuserId;
	}

	@GetMapping("/details/{quizId}/{superuserId}")
	public String getQuizDetailsPage(Model model, @PathVariable String quizId, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		var studentListSubmitted = assignmentService.getStudentInfoByQuizSubmitted(quizId);
		var studentListNotSubmitted = assignmentService.getStudentInfoByQuizNotSubmitted(quizId);

		model.addAttribute("quiz", quizService.getQuizById(quizId));
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("studentListSubmitted", studentListSubmitted);
		model.addAttribute("noQuizzes", studentListSubmitted.isEmpty());
		model.addAttribute("studentListNotSubmitted", studentListNotSubmitted);
		model.addAttribute("allSubmitted", studentListNotSubmitted.isEmpty());
		model.addAttribute("submissionsNumber", studentListSubmitted.size());
		model.addAttribute("studentsNumber", studentListSubmitted.size() + studentListNotSubmitted.size());

		return "quiz-details";
	}

	@GetMapping("/answers/{quizId}/{studentId}")
	public String getAnswersPage(Model model, @PathVariable String quizId, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		var quiz = quizService.getQuizById(quizId);
		model.addAttribute("quiz", quiz);
		model.addAttribute("studentName", studentService.getStudentById(studentId).getName());
		model.addAttribute("questionMap", questionService.getQuestionMapForStudent(quizId, studentId));
		model.addAttribute("superuserId", quiz.getSuperuserId());

		return "quiz-answers";
	}

}
