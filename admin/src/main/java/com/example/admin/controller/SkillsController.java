package com.example.admin.controller;

import com.example.admin.entity.Page;
import com.example.admin.entity.Skill;
import com.example.admin.service.CategoryService;
import com.example.admin.service.NavbarService;
import com.example.admin.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class SkillsController {

	final NavbarService navbarService;
	final SkillService skillService;
	final CategoryService categoryService;

	@GetMapping("/skills")
	String getSkillsPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.SKILLS, model);
		model.addAttribute("categoryList", categoryService.getCategoryModelList());
		model.addAttribute("skillList", skillService.getSkillModelList());
		model.addAttribute("duplicate", duplicate);
		return "skills";
	}

	@PostMapping("/add-skill")
	public String addSkill(@ModelAttribute("skill") Skill skill) {
		if (skillService.isDuplicate(skill.getName())) {
			return "redirect:/skills?duplicate=true";
		} else {
			skillService.saveSkill(skill);
		}

		return "redirect:/skills";
	}

	@PostMapping("/delete-skill/{skillId}")
	public String deleteSkill(@PathVariable final String skillId) {
		// Delete all related evaluations, quizzes etc
		skillService.deleteSkill(skillId);
		return "redirect:/skills";
	}

	@PostMapping("/edit-skill/{id}")
	public String editSkill(@ModelAttribute("skill") Skill skill, @PathVariable("id") final String id) {
		skill.setId(id);
		if (skillService.isDuplicateExcept(skill.getName(), skill.getId())) {
			return "redirect:/skills?duplicate=true";
		} else {
			skillService.saveSkill(skill);
		}
		return "redirect:/skills";
	}
}
