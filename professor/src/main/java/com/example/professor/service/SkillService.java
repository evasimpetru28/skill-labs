package com.example.professor.service;

import com.example.professor.entity.Assignment;
import com.example.professor.entity.Evaluation;
import com.example.professor.entity.Quiz;
import com.example.professor.entity.Student;
import com.example.professor.dto.SkillChartDataDto;
import com.example.professor.dto.SkillDetailsDto;
import com.example.professor.dto.SkillStatisticsDto;
import com.example.professor.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillService {
	final QuizRepository quizRepository;
	final SkillRepository skillRepository;
	final StudentRepository studentRepository;
	final AssignmentRepository assignmentRepository;
	final EvaluationRepository evaluationRepository;

	public SkillDetailsDto getSkillInformation(String skillId) {
		return skillRepository.getSkillDetailsById(skillId);
	}

	private String calculateMasteryLevel(Evaluation eval) {
		// Initialize counters and sum for non-null values
		double count = 0.0;
		double sum = 0.0;
		
		// Weight factors for each aspect
		double interestWeight = 0.2;  // 20% weight
		double knowledgeWeight = 0.5; // 50% weight
		double experienceWeight = 0.3; // 30% weight
		
		// Calculate weighted average of non-null values
		if (eval.getInterest() != null) {
			sum += eval.getInterest() * interestWeight;
			count += interestWeight;
		}
		if (eval.getKnowledge() != null) {
			sum += eval.getKnowledge() * knowledgeWeight;
			count += knowledgeWeight;
		}
		if (eval.getExperience() != null) {
			sum += eval.getExperience() * experienceWeight;
			count += experienceWeight;
		}
		
		// Calculate final weighted average
		double weightedAverage = count > 0 ? sum / count : 0;
		
		// Determine mastery level based on weighted average
		if (weightedAverage >= 8.0) {
			return "ADVANCED";
		} else if (weightedAverage >= 5.0) {
			return "INTERMEDIATE";
		} else {
			return "BEGINNER";
		}
	}

	public SkillStatisticsDto getSkillStatistics(String skillId) {
		// Get all students that answered quizzes for this skill
		List<Student> enrolledStudents = studentRepository.findStudentsByAnsweredQuizSkillId(skillId);
		int totalStudents = enrolledStudents.size();

		// Get all evaluations for this skill
		List<Evaluation> evaluations = evaluationRepository.findBySkillId(skillId);
		int evaluationCount = evaluations.size();

		// Get all quizzes for this skill
		List<Quiz> quizzes = quizRepository.findBySkillId(skillId);
		int quizCount = quizzes.size();

		// Calculate average score across all quizzes
		double averageScore = quizzes.stream()
				.flatMap(quiz -> assignmentRepository.findAllByQuizId(quiz.getId()).stream())
				.filter(assignment -> assignment.getScore() != null)
				.mapToDouble(Assignment::getScore)
				.average()
				.orElse(0.0);

		// Calculate skill mastery distribution
		List<String> masteryLevels = evaluations.stream()
				.map(this::calculateMasteryLevel)
				.toList();
		
		int beginnerCount = (int) masteryLevels.stream()
				.filter(mastery -> mastery.equals("BEGINNER"))
				.count();
		int intermediateCount = (int) masteryLevels.stream()
				.filter(mastery -> mastery.equals("INTERMEDIATE"))
				.count();
		int advancedCount = (int) masteryLevels.stream()
				.filter(mastery -> mastery.equals("ADVANCED"))
				.count();

		// Calculate percentages
		double totalEvaluations = beginnerCount + intermediateCount + advancedCount;
		double beginnerPercentage = totalEvaluations > 0 ? (beginnerCount / totalEvaluations) * 100 : 0;
		double intermediatePercentage = totalEvaluations > 0 ? (intermediateCount / totalEvaluations) * 100 : 0;
		double advancedPercentage = totalEvaluations > 0 ? (advancedCount / totalEvaluations) * 100 : 0;

		return SkillStatisticsDto.builder()
				.totalStudents(totalStudents)
				.evaluationCount(evaluationCount)
				.quizCount(quizCount)
				.averageScore(averageScore)
				.beginnerCount(beginnerCount)
				.intermediateCount(intermediateCount)
				.advancedCount(advancedCount)
				.beginnerPercentage(beginnerPercentage)
				.intermediatePercentage(intermediatePercentage)
				.advancedPercentage(advancedPercentage)
				.build();
	}

	public SkillChartDataDto getSkillChartData(String skillId) {
		// Get all evaluations for this skill
		List<Evaluation> evaluations = evaluationRepository.findBySkillId(skillId);

		// Get evaluation trends (last 6 months)
		List<String> evaluationMonths = new ArrayList<>();
		List<Integer> evaluationCounts = new ArrayList<>();
		LocalDate now = LocalDate.now();
		for (int i = 5; i >= 0; i--) {
			LocalDate month = now.minusMonths(i);
			String monthStr = month.format(DateTimeFormatter.ofPattern("MMM yyyy"));
			evaluationMonths.add(monthStr);
			
			int count = (int) evaluations.stream()
					.filter(eval -> {
						LocalDate evalDate = eval.getCreatedAt().toLocalDate();
						return evalDate.getMonth() == month.getMonth() && 
							   evalDate.getYear() == month.getYear();
					})
					.count();
			evaluationCounts.add(count);
		}

		// Get quiz performance data
		List<Quiz> quizzes = quizRepository.findBySkillId(skillId);
		List<String> quizNames = quizzes.stream()
				.map(Quiz::getName)
				.collect(Collectors.toList());
		List<Double> quizScores = quizzes.stream()
				.map(quiz ->
					 assignmentRepository.findAllByQuizId(quiz.getId()).stream()
							.filter(assignment -> assignment.getScore() != null)
							.mapToDouble(Assignment::getScore)
							.average()
							.orElse(0.0)
				)
				.toList();

		return SkillChartDataDto.builder()
				.evaluationMonths(evaluationMonths)
				.evaluationCounts(evaluationCounts)
				.quizNames(quizNames)
				.quizScores(quizScores)
				.build();
	}
}
