package com.example.admin.service;

import com.example.admin.entity.Evaluation;
import com.example.admin.model.PopularSkillModel;
import com.example.admin.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {

	final EvaluationRepository evaluationRepository;

	public void deleteAllEvaluationsForStudent(String studentId) {
		evaluationRepository.deleteAllByStudentId(studentId);
	}

	public void deleteAllEvaluationsForSkill(String skillId) {
		evaluationRepository.deleteAllBySkillId(skillId);
	}

	public List<Evaluation> getAllEvaluations() {
		return evaluationRepository.findAll();
	}

	public List<PopularSkillModel> getPopularSkills(int limit) {
		return evaluationRepository.findPopularSkills(limit);
	}

	public Integer getStudentsWithEvaluations() {
		return evaluationRepository.countStudentsWithEvaluations();
	}

	public Double getAverageEvaluationsPerStudent() {
		return evaluationRepository.getAverageEvaluationsPerStudent();
	}

}
