package com.example.professor.controller;

import java.util.List;

import com.example.professor.service.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.professor.entity.Page;
import com.example.professor.model.CategoryWithSkills;
import com.example.professor.model.SkillChartDataDTO;
import com.example.professor.service.CategoryService;
import com.example.professor.service.NavbarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AllSkillsController {

	final SkillService skillService;
	final NavbarService navbarService;
	final CategoryService categoryService;

    @GetMapping("/all-skills/{superuserId}")
	public String getAllSkillsPage(Model model, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		
        List<CategoryWithSkills> categories = categoryService.getAllCategoriesAndSkills();

        model.addAttribute("noSkills", categories.isEmpty());
        model.addAttribute("categories", categories);
        model.addAttribute("superuserId", superuserId);
		return "all-skills";
	}

	@GetMapping("/skill-details/{skillId}/overview/{superuserId}")
	public String getSkillDetailsOverviewPage(Model model, @PathVariable String skillId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);

		model.addAttribute("skill", skillService.getSkillInformation(skillId));
		model.addAttribute("statistics", skillService.getSkillStatistics(skillId));
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("isOverview", true);
		return "skill-details-overview";
	}

	@GetMapping("/skill-details/{skillId}/students-self-evaluations/{superuserId}")
	public String getSkillDetailsEvaluationsPage(Model model, @PathVariable String skillId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);

		model.addAttribute("skill", skillService.getSkillInformation(skillId));
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("isEvaluations", true);
		return "skill-details-evaluations";
	}

	@GetMapping("/skill-details/{skillId}/quizzes/{superuserId}")
	public String getSkillDetailsQuizzesPage(Model model, @PathVariable String skillId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);

		model.addAttribute("skill", skillService.getSkillInformation(skillId));
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("isQuizzes", true);
		return "skill-details-quizzes";
	}

	@GetMapping("/api/skill-details/{skillId}/stats")
	@ResponseBody
	public SkillChartDataDTO getSkillChartData(@PathVariable String skillId) {
		return skillService.getSkillChartData(skillId);
	}
}
