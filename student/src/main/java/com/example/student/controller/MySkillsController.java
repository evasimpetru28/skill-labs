package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.service.NavbarService;
import com.example.student.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class MySkillsController {

	final NavbarService navbarService;
	final SkillService skillService;

	@GetMapping("/my-skills/{studentId}")
	public String getMySkillsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.MY_SKILLS, model);
		var evaluationsForStudent = skillService.getEvaluationsForStudent(studentId);
		model.addAttribute("studentId", studentId);
		model.addAttribute("evaluationsList", evaluationsForStudent);
		model.addAttribute("noEvaluations", evaluationsForStudent.isEmpty());

		return "my-skills";
	}

}
