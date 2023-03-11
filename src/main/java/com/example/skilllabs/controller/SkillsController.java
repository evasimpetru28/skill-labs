package com.example.skilllabs.controller;

import com.example.skilllabs.entity.Category;
import com.example.skilllabs.entity.Page;
import com.example.skilllabs.entity.Skill;
import com.example.skilllabs.service.CategoryService;
import com.example.skilllabs.service.NavbarService;
import com.example.skilllabs.service.SkillService;
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
    public String deleteSkill(@PathVariable String skillId) {
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
