package com.example.professor.controller;

import com.example.professor.entity.Assignment;
import com.example.professor.entity.Page;
import com.example.professor.service.*;
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
	final SuperuserService superuserService;
	final AssignmentService assignmentService;

	@GetMapping("/assign-students/{quizId}")
	public String getAssignStudentsPage(Model model, @PathVariable String quizId) {
		navbarService.activateNavbarTab(Page.MY_QUIZZES, model);
		var quiz = quizService.getQuizById(quizId);
		model.addAttribute("studentList", studentService.getAllStudentsNotAssignedByQuiz(quizId));
		model.addAttribute("assignedStudents", assignmentService.getAssignedStudentsOfQuiz(quizId));
		model.addAttribute("quiz", quiz);
		model.addAttribute("superuserId", quiz.getSuperuserId());
		model.addAttribute("name", superuserService.getSuperuserById(quiz.getSuperuserId()).getName());

		return "assign-students";
	}

	@PostMapping("edit-assigned/{quizId}")
	public String editAssignedQuiz(@PathVariable String quizId) {
		return "redirect:/assign-students/" + quizId;
	}

	@PostMapping("/next/{quizId}")
	public String sendQuiz(@PathVariable String quizId) {
		return "redirect:/assign-students/" + quizId;
	}

	@PostMapping("/assign/{quizId}/{name}")
	public String assignStudent(@PathVariable String quizId, @PathVariable String name) {
		var student = studentService.getStudentByName(name);
		var assignment = new Assignment();
		assignment.setQuizId(quizId);
		assignment.setStudentId(student.getId());
		assignmentService.saveAssignment(assignment);

		return "redirect:/assign-students/" + quizId;
	}

	@PostMapping("/remove-student/{quizId}/{studentId}")
	public String removeAssignedStudent(@PathVariable String quizId, @PathVariable String studentId) {
		var assignment = assignmentService.getAssignmentByStudentAndQuiz(studentId, quizId);
		assignmentService.deleteAssignment(assignment);
		return "redirect:/assign-students/" + quizId;
	}

}
