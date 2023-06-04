package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class AssignmentsController {

	final NavbarService navbarService;
	@GetMapping("/assignments/{studentId}")
	public String getAssignmentsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ASSIGNMENTS, model);
		model.addAttribute("studentId", studentId);
		return "assignments";
	}

}
