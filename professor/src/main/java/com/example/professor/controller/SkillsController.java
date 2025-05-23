package com.example.professor.controller;

import com.example.professor.dto.CategoryWithSkillsDto;
import com.example.professor.dto.SkillChartDataDto;
import com.example.professor.entity.Page;
import com.example.professor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SkillsController {

	final QuizService quizService;
	final SkillService skillService;
	final NavbarService navbarService;
	final StudentService studentService;
	final CategoryService categoryService;
	final SuperuserService superuserService;

    @GetMapping("/all-skills/{superuserId}")
	public String getAllSkillsPage(Model model, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		
        List<CategoryWithSkillsDto> categories = categoryService.getAllCategoriesAndSkills(superuserId);
		var superuser = superuserService.getSuperuserById(superuserId);

		model.addAttribute("noSkills", categories.isEmpty());
		model.addAttribute("categories", categories);
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		model.addAttribute("superuser", superuserService.getSuperuserById(superuserId).getName());
		return "all-skills";
	}

    @GetMapping("/bookmarked-skills/{superuserId}")
    public String getBookmarkedSkillsPage(Model model, @PathVariable String superuserId) {
        navbarService.activateNavbarTab(Page.BOOKMARKED_SKILLS, model);
        
        var bookmarkedSkills = categoryService.getAllBookmarkedCategoriesAndSkills(superuserId);
		var superuser = superuserService.getSuperuserById(superuserId);

		model.addAttribute("noSkills", bookmarkedSkills.isEmpty());
		model.addAttribute("bookmarkedSkills", bookmarkedSkills);
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		model.addAttribute("superuser", superuserService.getSuperuserById(superuserId).getName());
        return "bookmarked-skills";
    }

    @PostMapping("/api/skill/{skillId}/bookmark/{superuserId}")
    @ResponseBody
    public ResponseEntity<Void> toggleBookmark(@PathVariable String skillId, @PathVariable String superuserId) {
        skillService.toggleBookmark(skillId, superuserId);
        return ResponseEntity.ok().build();
    }

	@GetMapping("/skill-details/{skillId}/overview/{superuserId}")
	public String getSkillDetailsOverviewPage(Model model, @PathVariable String skillId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);
		var superuser = superuserService.getSuperuserById(superuserId);

		model.addAttribute("skill", skillService.getSkillInformation(skillId, superuserId));
		model.addAttribute("statistics", skillService.getSkillStatistics(skillId));
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		model.addAttribute("superuser", superuserService.getSuperuserById(superuserId).getName());
		model.addAttribute("isOverview", true);
		return "skill-details-overview";
	}

	@GetMapping("/skill-details/{skillId}/students-self-evaluations/{superuserId}")
	public String getSkillDetailsEvaluationsPage(Model model, @PathVariable String skillId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);

		var evaluations = studentService.getAllStudentsSelfEvaluationBySkillId(skillId);
		var superuser = superuserService.getSuperuserById(superuserId);
		model.addAttribute("skill", skillService.getSkillInformation(skillId, superuserId));
		model.addAttribute("evaluations", evaluations);
		model.addAttribute("noEvaluations", evaluations.isEmpty());
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		model.addAttribute("superuser", superuserService.getSuperuserById(superuserId).getName());
		model.addAttribute("isEvaluations", true);
		return "skill-details-evaluations";
	}

	@GetMapping("/skill-details/{skillId}/quizzes/{superuserId}")
	public String getSkillDetailsQuizzesPage(Model model, @PathVariable String skillId, @PathVariable String superuserId) {
		navbarService.activateNavbarTab(Page.ALL_SKILLS, model);

		var quizzes = quizService.getQuizListBySkillId(skillId, superuserId);
		var superuser = superuserService.getSuperuserById(superuserId);
		model.addAttribute("skill", skillService.getSkillInformation(skillId, superuserId));
		model.addAttribute("quizzes", quizzes);
		model.addAttribute("noQuizzes", quizzes.isEmpty());
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		model.addAttribute("superuser", superuserService.getSuperuserById(superuserId).getName());
		model.addAttribute("isQuizzes", true);
		return "skill-details-quizzes";
	}

	@GetMapping("/api/skill-details/{skillId}/stats")
	@ResponseBody
	public SkillChartDataDto getSkillChartData(@PathVariable String skillId) {
		return skillService.getSkillChartData(skillId);
	}
}
