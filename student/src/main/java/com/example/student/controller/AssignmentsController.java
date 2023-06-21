package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.entity.Response;
import com.example.student.service.*;
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
	final AssignmentService assignmentService;

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
		model.addAttribute("reviewMode", false);

		return "answer-quiz";
	}

	@PostMapping("add-response/{optionId}/{questionId}/{studentId}")
	public String addResponse(@PathVariable String optionId, @PathVariable String questionId, @PathVariable String studentId) {
		var response = new Response();
		response.setOptionId(optionId);
		response.setQuestionId(questionId);
		response.setStudentId(studentId);
		responseService.saveResponse(response);
		return "redirect:/answer-quiz/" + questionService.getQuestionById(questionId).getQuizId() +
			   "/" + studentId;
	}

	@PostMapping("delete-response/{responseId}")
	public String deleteResponse(@PathVariable String responseId) {
		var response = responseService.getResponseById(responseId);
		var question = questionService.getQuestionById(response.getQuestionId());
		responseService.deleteResponseById(responseId);
		return "redirect:/answer-quiz/" + question.getQuizId() + "/" + response.getStudentId();
	}

	@PostMapping("check-answers/{quizId}/{studentId}")
	public String checkAnswers(@PathVariable String quizId, @PathVariable String studentId) {
		var questionNumber = questionService.getQuestionNumberForQuiz(quizId);
		var questions = questionService.getAllQuestionIdsByQuizId(quizId);
		var responseNumber = responseService.getAnsweredQuestionsForStudent(studentId, questions);
		if (questionNumber == responseNumber) {
			var score = responseService.calculateScore(questions, studentId);
			var assignment = assignmentService.getAssignmentByStudentAndQuiz(studentId, quizId);
			assignment.setScore(score);
			assignmentService.saveAssignment(assignment);
			return "redirect:/assignments/" + studentId;
		} else {
			//TODO: Error messaje
			return "redirect:/answer-quiz/" + quizId + "/" + studentId;
		}
	}

	@GetMapping("review-quiz/{quizId}/{studentId}")
	public String getReviewQuizPage(Model model, @PathVariable String quizId, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ASSIGNMENTS, model);
		model.addAttribute("studentId", studentId);
		model.addAttribute("quiz", quizService.getQuizById(quizId));
		model.addAttribute("questionMap", questionService.getQuestionMap(quizId, studentId));
		model.addAttribute("reviewMode", true);

		return "answer-quiz";
	}

}
