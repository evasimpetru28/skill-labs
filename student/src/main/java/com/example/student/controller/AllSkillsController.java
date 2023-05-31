package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.service.CategoryService;
import com.example.student.service.EvaluationService;
import com.example.student.service.NavbarService;
import com.example.student.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AllSkillsController {

	final NavbarService navbarService;
	final SkillService skillService;
	final CategoryService categoryService;
	final EvaluationService evaluationService;

	@GetMapping("/all-skills/{studentId}")
	public String getAllSkillsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		model.addAttribute("studentId", studentId);
		model.addAttribute("skillList", skillService.getAllSkillsAndEvaluationsForStudent(studentId));
		return "all-skills";
	}

	@PostMapping("/delete-evaluation/{skillName}/{criteria}/{studentId}")
	public ResponseEntity<String> deleteEvaluation(@PathVariable String skillName, @PathVariable String criteria, @PathVariable String studentId) {
		//TODO: Get logged user id from session
		try {
			evaluationService.removeEvaluationCriteriaForSkillAndStudent(skillName, studentId, criteria);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Error deleting the evaluation for student with id {} and skill with name {}: ", studentId, skillName);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
