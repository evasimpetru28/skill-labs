package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.service.CategoryService;
import com.example.student.service.NavbarService;
import com.example.student.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AllSkillsController {

	final NavbarService navbarService;
	final SkillService skillService;
	final CategoryService categoryService;

	@GetMapping("/all-skills/{studentId}")
	String getAllSkillsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		model.addAttribute("studentId", studentId);
		model.addAttribute("skillList", skillService.getAllSkillsAndEvaluationsForStudent(studentId));
		return "all-skills";
	}

	@ResponseBody
	@RequestMapping(value = "/evaluate/{tempId}")
	public String getSearchResultViaAjax(@PathVariable(value = "tempId") String id)
	{
		System.out.println("PRIMIT: " + id);
		return String.valueOf(id);
	}

}
