package com.example.admin.controller;

import com.example.admin.entity.Page;
import com.example.admin.model.PopularSkillModel;
import com.example.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class DashboardController {

	final NavbarService navbarService;
	final StudentService studentService;
	final SuperuserService superuserService;
	final SkillService skillService;
	final CategoryService categoryService;
	final EvaluationService evaluationService;
	final AdminService adminService;

	@GetMapping("/dashboard")
	String getDashboardPage(Model model) {
		navbarService.activateNavbarTab(Page.DASHBOARD, model);

		// Get all entities
		var students = studentService.getAllStudents();
		var superusers = superuserService.getAllSuperusers();
		var skills = skillService.getAllSkills();
		var categories = categoryService.getAllCategories();
		var evaluations = evaluationService.getAllEvaluations();
		var admins = adminService.getAllAdmins();

		// Calculate user statistics
		var professorCount = superusers.stream().filter(s -> "PROFESSOR".equals(s.getType())).count();
		var companyCount = superusers.stream().filter(s -> "COMPANY".equals(s.getType())).count();
		var totalUsers = students.size() + superusers.size() + admins.size();
		Map<String, Number> userStats = new HashMap<>();
		userStats.put("studentCount", students.size());
		userStats.put("professorCount", professorCount);
		userStats.put("companyCount", companyCount);
		userStats.put("adminCount", admins.size());
		userStats.put("totalUsers", totalUsers);

		// Calculate skills and categories statistics
		Map<String, Long> skillsByCategory = skills.stream()
				.collect(Collectors.groupingBy(
					skill -> categoryService.getCategoryById(skill.getCategoryId()).getName(),
					HashMap::new,
					Collectors.counting()
				));
		Map<String, Object> skillStats = new HashMap<>();
		skillStats.put("categories", new ArrayList<>(skillsByCategory.keySet()));
		skillStats.put("counts", new ArrayList<>(skillsByCategory.values()));
		skillStats.put("totalSkills", skills.size());
		skillStats.put("totalCategories", categories.size());

		// Calculate evaluation statistics
		var studentsWithEvals = evaluationService.getStudentsWithEvaluations();
		var studentsWithoutEvals = students.size() - studentsWithEvals;
		var avgEvaluationsPerStudent = evaluationService.getAverageEvaluationsPerStudent();
		var mostEvaluatedSkills = evaluationService.getPopularSkills(10);
		
		Map<String, Object> evaluationStats = new HashMap<>();
		evaluationStats.put("totalEvaluations", evaluations.size());
		evaluationStats.put("studentsWithEvals", studentsWithEvals);
		evaluationStats.put("studentsWithoutEvals", studentsWithoutEvals);
		evaluationStats.put("avgEvaluationsPerStudent", avgEvaluationsPerStudent);
		evaluationStats.put("popularSkillNames", mostEvaluatedSkills.stream().map(PopularSkillModel::getName).collect(Collectors.toList()));
		evaluationStats.put("popularSkillCounts", mostEvaluatedSkills.stream().map(PopularSkillModel::getCount).collect(Collectors.toList()));

		// Add all stats to model
		model.addAttribute("userStats", userStats);
		model.addAttribute("skillStats", skillStats);
		model.addAttribute("evaluationStats", evaluationStats);

		return "dashboard";
	}
}
