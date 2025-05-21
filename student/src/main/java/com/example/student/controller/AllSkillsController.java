package com.example.student.controller;

import com.example.student.entity.Page;
import com.example.student.service.EvaluationService;
import com.example.student.service.NavbarService;
import com.example.student.service.SkillService;
import com.example.student.service.StudentService;
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
	final SkillService skillService;
	final NavbarService navbarService;
	final StudentService studentService;
	final EvaluationService evaluationService;

	@GetMapping("/all-skills/{studentId}")
	public String getAllSkillsPage(Model model, @PathVariable String studentId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		var skillsByCategory = skillService.getAllSkillsAndEvaluationsForStudent(studentId);
		model.addAttribute("studentId", studentId);
		model.addAttribute("name", studentService.getStudentById(studentId).getName());
		model.addAttribute("skillsByCategory", skillsByCategory);
		return "all-skills";
	}

	@PostMapping("/delete-evaluation/{skillId}/{criteria}/{studentId}")
	public ResponseEntity<String> deleteEvaluation(@PathVariable String skillId, @PathVariable String criteria, @PathVariable String studentId) {
		//TODO: Get logged user id from session
		try {
			evaluationService.removeEvaluationCriteriaForSkillAndStudent(skillId, studentId, criteria);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Error deleting the evaluation for student with id {} and skill with name {}: {}", studentId, skillId, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/reevaluate/{skillId}/{criteria}/{starsNumber}/{studentId}")
	public ResponseEntity<String> reevaluation(@PathVariable String skillId, @PathVariable String criteria, @PathVariable Integer starsNumber, @PathVariable String studentId) {
		//TODO: Get logged user id from session
		try {
			evaluationService.reevaluateCriteriaForSkillAndStudent(skillId, criteria, starsNumber, studentId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Error reevaluating the skill {} for student with id {}", skillId, studentId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
