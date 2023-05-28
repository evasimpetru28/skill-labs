package com.example.admin.service;

import com.example.admin.repository.EvaluationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class EvaluationService {

	final EvaluationRepository evaluationRepository;

	public void deleteAllEvaluationsForStudent(String studentId) {
		evaluationRepository.deleteAllByStudentId(studentId);
	}

	public void deleteAllEvaluationsForSkill(String skillId) {
		evaluationRepository.deleteAllBySkillId(skillId);
	}

}
