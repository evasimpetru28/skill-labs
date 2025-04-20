package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.model.CategoryWithSkills;
import com.example.student.service.NavbarService;
import com.example.student.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MySkillsController {

	final NavbarService navbarService;
	final SkillService skillService;

	@GetMapping("/my-skills/{studentId}")
	public String getMySkillsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.MY_SKILLS, model);
		var evaluationsForStudent = skillService.getEvaluationsForStudentByCategory(studentId);
		
		List<CategoryWithSkills> categories = new ArrayList<>();
		evaluationsForStudent.forEach((category, skills) -> {
			categories.add(new CategoryWithSkills(category, skills));
		});
		
		model.addAttribute("studentId", studentId);
		model.addAttribute("categories", categories);
		model.addAttribute("noEvaluations", evaluationsForStudent.isEmpty());

		return "my-skills";
	}

}
