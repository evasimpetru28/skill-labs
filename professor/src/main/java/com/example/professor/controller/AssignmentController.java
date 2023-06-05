package com.example.professor.controller;

import com.example.professor.entity.Assignment;
import com.example.professor.entity.Page;
import com.example.professor.service.AssignmentService;
import com.example.professor.service.NavbarService;
import com.example.professor.service.QuizService;
import com.example.professor.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AssignmentController {

	final QuizService quizService;
	final NavbarService navbarService;
	final StudentService studentService;
	final AssignmentService assignmentService;

	@GetMapping("assign-students/{quizId}")
	public String getAssignStudentsPage(Model model, @PathVariable String quizId) {
		navbarService.activateNavbarTab(Page.QUIZZES, model);
		var quiz = quizService.getQuizById(quizId);
		model.addAttribute("studentList", studentService.getAllStudents());
		model.addAttribute("quiz", quiz);
		model.addAttribute("superuserId", quiz.getSuperuserId());
		return "assign-students";
	}

	@PostMapping("assign/{quizId}/{name}")
	public void assignStudent(Model model, @PathVariable String quizId, @PathVariable String name) {
		navbarService.activateNavbarTab(Page.QUIZZES, model);

		var student = studentService.getStudentByName(name);

		var assignment = new Assignment();
		assignment.setQuizId(quizId);
		assignment.setStudentId(student.getId());
		assignmentService.saveAssignment(assignment);

	}
}
