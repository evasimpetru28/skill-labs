package com.example.professor.controller;

import com.example.professor.entity.Page;
import com.example.professor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class DashboardController {

	final QuizService quizService;
	final NavbarService navbarService;
	final CategoryService categoryService;
	final SuperuserService superuserService;
	final AssignmentService assignmentService;
	final EvaluationService evaluationService;

	@GetMapping("/dashboard/{superuserId}")
	String getDashboardPage(Model model, @PathVariable String superuserId) {
		//TODO: Get logged user id from session
		navbarService.activateNavbarTab(Page.DASHBOARD, model);
		var superuser = superuserService.getSuperuserById(superuserId);
		model.addAttribute("superuserId", superuserId);
		model.addAttribute("name", superuser.getName());
		model.addAttribute("isProfessor", "PROFESSOR".equals(superuser.getType()));
		return "dashboard";
	}

	@GetMapping("/api/dashboard/stats/{superuserId}")
	@ResponseBody
	Map<String, Object> getDashboardStats(@PathVariable String superuserId) {
		Map<String, Object> stats = new HashMap<>();
		
		// Get all assigned quizzes
		var quizzesWithAssignations = quizService.getQuizzesWithAssignmentsForSuperuserId(superuserId);

		// Quiz Statistics
		Map<String, Object> quizStats = new HashMap<>();
		quizStats.put("totalQuizzes", quizService.getMyQuizNumber(superuserId));
		
		// Calculate average scores for personal quizzes
		double totalPersonalScore = 0;
		int totalPersonalSubmissions = 0;
		
		// Score distribution buckets (0-20, 21-40, 41-60, 61-80, 81-100)
		int[] scoreDistribution = new int[5];
		
		// Monthly data
		Map<String, Integer> monthlyCompletions = new HashMap<>();
		LocalDate now = LocalDate.now();
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM yyyy");
		
		// Initialize last 6 months
		for (int i = 5; i >= 0; i--) {
			LocalDate month = now.minusMonths(i);
			monthlyCompletions.put(month.format(monthFormatter), 0);
		}
		
		// Process personal quizzes
		for (var quiz : quizzesWithAssignations) {
			var submissions = assignmentService.getStudentInfoByQuizSubmitted(quiz.getId());
			
			for (var submission : submissions) {
				totalPersonalScore += submission.getScore();
				totalPersonalSubmissions++;

				// Add to score distribution
				int bucket = Math.min((submission.getScore() / 20), 4);
				scoreDistribution[bucket]++;

				// Add to monthly completions
				String monthKey = now.format(monthFormatter);
				monthlyCompletions.put(monthKey, monthlyCompletions.getOrDefault(monthKey, 0) + 1);
			}
		}
		
		// Calculate average score for personal quizzes
		double personalAvgScore = totalPersonalSubmissions == 0 ? 0 : totalPersonalScore / totalPersonalSubmissions;
		quizStats.put("myQuizzesAvgScore", Math.round(personalAvgScore * 100.0) / 100.0);
		
		// Calculate average score for public quizzes
		var publicQuizzes = quizService.getPublicQuizzes();
		double totalPublicScore = 0;
		int totalPublicSubmissions = 0;
		
		for (var quiz : publicQuizzes) {
			var submissions = assignmentService.getStudentInfoByQuizSubmitted(quiz.getId());
			
			for (var submission : submissions) {
				if (submission.getScore() != null) {
					totalPublicScore += submission.getScore();
					totalPublicSubmissions++;
				}
			}
		}
		
		double publicAvgScore = totalPublicSubmissions == 0 ? 0 : totalPublicScore / totalPublicSubmissions;
		quizStats.put("publicQuizzesAvgScore", Math.round(publicAvgScore * 100.0) / 100.0);
		
		// Get skills statistics
		var bookmarkedSkills = categoryService.getAllBookmarkedCategoriesAndSkills(superuserId);
		int totalBookmarkedSkills = bookmarkedSkills.stream()
				.mapToInt(category -> category.getSkills().size())
				.sum();
		
		Map<String, Object> skillStats = new HashMap<>();
		
		// Calculate student progress metrics
		int totalStudents = 0;
		int studentsWithHighScores = 0; // Students with average score >= 80
		
		// Track unique students across all quizzes
		Set<String> uniqueStudents = new HashSet<>();
		Set<String> highPerformingStudents = new HashSet<>();
		
		for (var quiz : quizzesWithAssignations) {
			var submissions = assignmentService.getStudentInfoByQuizSubmitted(quiz.getId());
			for (var submission : submissions) {
				String studentId = submission.getStudentId();
				uniqueStudents.add(studentId);
				
				if (submission.getScore() != null && submission.getScore() >= 80) {
					highPerformingStudents.add(studentId);
				}
			}
		}
		
		totalStudents = uniqueStudents.size();
		studentsWithHighScores = highPerformingStudents.size();
		double highPerformersPercentage = totalStudents == 0 ? 0 : 
			(double) studentsWithHighScores / totalStudents * 100;
		
		skillStats.put("totalStudents", totalStudents);
		skillStats.put("highPerformersCount", studentsWithHighScores);
		skillStats.put("highPerformersPercentage", Math.round(highPerformersPercentage * 100.0) / 100.0);
		
		// Calculate student engagement metrics
		Map<String, Integer> studentActivity = new HashMap<>();
		
		// Count quiz participations
		for (var quiz : quizzesWithAssignations) {
			var submissions = assignmentService.getStudentInfoByQuizSubmitted(quiz.getId());
			for (var submission : submissions) {
				String studentId = submission.getStudentId();
				studentActivity.merge(studentId, 1, Integer::sum);
			}
		}
		
		// Count evaluations
		for (var category : categoryService.getAllCategoriesAndSkills(superuserId)) {
			for (var skill : category.getSkills()) {
				var evaluations = evaluationService.getEvaluationsBySkillId(skill.getId());
				for (var evaluation : evaluations) {
					studentActivity.merge(evaluation.getStudentId(), 1, Integer::sum);
				}
			}
		}
		
		// Calculate engagement levels
		int highlyEngagedCount = 0;
		int totalActivities = quizzesWithAssignations.size() + categoryService.getAllCategoriesAndSkills(superuserId)
				.stream()
				.mapToInt(category -> category.getSkills().size())
				.sum();
		
		// Consider a student highly engaged if they've participated in at least 70% of available activities
		double engagementThreshold = totalActivities * 0.7;
		for (var entry : studentActivity.entrySet()) {
			if (entry.getValue() >= engagementThreshold) {
				highlyEngagedCount++;
			}
		}
		
		double engagementRate = totalStudents == 0 ? 0 : 
			(double) highlyEngagedCount / totalStudents * 100;
		
		skillStats.put("highlyEngagedCount", highlyEngagedCount);
		skillStats.put("engagementRate", Math.round(engagementRate * 100.0) / 100.0);
		
		// Calculate skill mastery metrics
		Map<String, Map<String, Double>> studentSkillLevels = new HashMap<>();
		Map<String, Integer> studentTotalSkills = new HashMap<>();
		Map<String, Integer> studentMasteredSkills = new HashMap<>();
		
		// Count mastery levels across all skills
		int beginnerCount = 0;
		int intermediateCount = 0;
		int advancedCount = 0;
		
		// Weight factors for each aspect
		double interestWeight = 0.2;  // 20% weight
		double knowledgeWeight = 0.5; // 50% weight
		double experienceWeight = 0.3; // 30% weight
		
		// Get all skills and their evaluations
		for (var category : categoryService.getAllCategoriesAndSkills(superuserId)) {
			for (var skill : category.getSkills()) {
				var evaluations = evaluationService.getEvaluationsBySkillId(skill.getId());
				for (var evaluation : evaluations) {
					String studentId = evaluation.getStudentId();
					
					// Initialize student's skill tracking if not exists
					studentSkillLevels.putIfAbsent(studentId, new HashMap<>());
					studentTotalSkills.putIfAbsent(studentId, 0);
					studentMasteredSkills.putIfAbsent(studentId, 0);
					
					// Calculate weighted average for this skill
					double count = 0.0;
					double sum = 0.0;
					
					if (evaluation.getInterest() != null) {
						sum += evaluation.getInterest() * interestWeight;
						count += interestWeight;
					}
					if (evaluation.getKnowledge() != null) {
						sum += evaluation.getKnowledge() * knowledgeWeight;
						count += knowledgeWeight;
					}
					if (evaluation.getExperience() != null) {
						sum += evaluation.getExperience() * experienceWeight;
						count += experienceWeight;
					}
					
					double weightedAverage = count > 0 ? sum / count : 0;
					studentSkillLevels.get(studentId).merge(skill.getName(), weightedAverage, Math::max);
					studentTotalSkills.merge(studentId, 1, Integer::sum);
					
					// Count skills where student has achieved mastery (weighted average >= 8.0)
					if (weightedAverage >= 8.0) {
						studentMasteredSkills.merge(studentId, 1, Integer::sum);
					}
					
					// Count mastery levels
					if (weightedAverage >= 8.0) {
						advancedCount++;
					} else if (weightedAverage >= 5.0) {
						intermediateCount++;
					} else {
						beginnerCount++;
					}
				}
			}
		}
		
		// Calculate mastery rate
		int studentsWithMastery = 0;
		for (var studentId : studentTotalSkills.keySet()) {
			int totalSkills = studentTotalSkills.get(studentId);
			int masteredSkills = studentMasteredSkills.get(studentId);
			
			// Consider a student has mastery if they've achieved mastery in at least 50% of their skills
			if (totalSkills > 0 && (double) masteredSkills / totalSkills >= 0.5) {
				studentsWithMastery++;
			}
		}
		
		double masteryRate = totalStudents == 0 ? 0 : 
			(double) studentsWithMastery / totalStudents * 100;
		
		skillStats.put("masteredSkillsCount", studentsWithMastery);
		skillStats.put("skillMasteryPercentage", Math.round(masteryRate * 100.0) / 100.0);
		
		// Add mastery level distribution
		skillStats.put("beginnerCount", beginnerCount);
		skillStats.put("intermediateCount", intermediateCount);
		skillStats.put("advancedCount", advancedCount);
		
		// Monthly statistics
		Map<String, Object> monthlyStats = new HashMap<>();
		monthlyStats.put("months", new ArrayList<>(monthlyCompletions.keySet()));
		monthlyStats.put("completions", new ArrayList<>(monthlyCompletions.values()));
		monthlyStats.put("scoreDistribution", scoreDistribution);

		// Get most evaluated skills (top 5)
		Map<String, Integer> skillEvaluationCounts = new HashMap<>();
		for (var category : categoryService.getAllCategoriesAndSkills(superuserId)) {
			for (var skill : category.getSkills()) {
				var evaluations = evaluationService.getEvaluationsBySkillId(skill.getId());
				skillEvaluationCounts.put(skill.getName(), evaluations.size());
			}
		}

		var topSkills = skillEvaluationCounts.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(5)
				.collect(Collectors.toList());

		List<String> topSkillNames = topSkills.stream().map(Map.Entry::getKey).collect(Collectors.toList());
		List<Integer> topSkillCounts = topSkills.stream().map(Map.Entry::getValue).collect(Collectors.toList());

		monthlyStats.put("topSkillNames", topSkillNames);
		monthlyStats.put("topSkillCounts", topSkillCounts);

		// Get average quiz scores by category
		Map<String, List<Double>> scoresByCategory = new HashMap<>();
		for (var category : categoryService.getAllCategoriesAndSkills(superuserId)) {
			String categoryName = category.getCategory().getName();
			scoresByCategory.put(categoryName, new ArrayList<>());

			for (var skill : category.getSkills()) {
				var quizzes = quizService.getQuizListBySkillId(skill.getId(), superuserId);
				for (var quiz : quizzes) {
					var submissions = assignmentService.getStudentInfoByQuizSubmitted(quiz.getId());
					double quizAvg = submissions.stream()
							.filter(s -> s.getScore() != null)
							.mapToDouble(s -> s.getScore())
							.average()
							.orElse(0.0);
					if (quizAvg > 0) {
						scoresByCategory.get(categoryName).add(quizAvg);
					}
				}
			}
		}

		// Calculate category averages
		List<String> categoryNames = new ArrayList<>();
		List<Double> categoryAverages = new ArrayList<>();

		for (var entry : scoresByCategory.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				categoryNames.add(entry.getKey());
				double avg = entry.getValue().stream()
						.mapToDouble(Double::doubleValue)
						.average()
						.orElse(0.0);
				categoryAverages.add(Math.round(avg * 100.0) / 100.0);
			}
		}

		monthlyStats.put("categoryNames", categoryNames);
		monthlyStats.put("categoryAverages", categoryAverages);
		
		// Add all stats to response
		stats.put("quizStats", quizStats);
		stats.put("skillStats", skillStats);
		stats.put("monthlyStats", monthlyStats);
		
		return stats;
	}
}
