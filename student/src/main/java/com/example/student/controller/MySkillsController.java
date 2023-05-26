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
public class MySkillsController {

	final NavbarService navbarService;

	@GetMapping("/my-skills/{studentId}")
	public String getMySkillsPage(Model model, @PathVariable String studentId) {
		model.addAttribute("studentId", studentId);
		navbarService.activateNavbarTab(Page.MY_SKILLS, model);
		return "my-skills";
	}

}
