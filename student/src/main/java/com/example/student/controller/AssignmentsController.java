package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.entity.Response;
import com.example.student.service.NavbarService;
import com.example.student.service.QuestionService;
import com.example.student.service.QuizService;
import com.example.student.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AssignmentsController {

	final QuizService quizService;
	final NavbarService navbarService;
	final QuestionService questionService;
	final ResponseService responseService;

	@GetMapping("/assignments/{studentId}")
	public String getAssignmentsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ASSIGNMENTS, model);
		var quizList = quizService.getQuizzesAssignedForStudent(studentId);
		model.addAttribute("studentId", studentId);
		model.addAttribute("quizList", quizList);
		model.addAttribute("noQuizzes", quizList.isEmpty());
		return "assignments";
	}

	@GetMapping("answer-quiz/{quizId}/{studentId}")
	public String getAnswerQuizPage(Model model, @PathVariable String quizId, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ASSIGNMENTS, model);
		model.addAttribute("studentId", studentId);
		model.addAttribute("quiz", quizService.getQuizById(quizId));
		model.addAttribute("questionMap", questionService.getQuestionMap(quizId, studentId));

		return "answer-quiz";
	}

	@PostMapping("add-response/{optionId}/{questionId}/{studentId}")
	public void addResponse(@PathVariable String optionId, @PathVariable String questionId, @PathVariable String studentId) {
		var response = new Response();
		response.setOptionId(optionId);
		response.setQuestionId(questionId);
		response.setStudentId(studentId);
		responseService.saveResponse(response);
	}

	@PostMapping("delete-response/{responseId}")
	public void deleteResponse(@PathVariable String responseId) {
		responseService.deleteResponseById(responseId);
	}

}
