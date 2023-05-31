package com.example.student.service;

import com.example.student.entity.Evaluation;
import com.example.student.repository.EvaluationRepository;
import com.example.student.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class EvaluationService {

	final EvaluationRepository evaluationRepository;
	final SkillRepository skillRepository;

	public void removeEvaluationCriteriaForSkillAndStudent(String skillName, String studentId, String criteria) {
		var skill = skillRepository.findByName(skillName);
		var evaluation = evaluationRepository.findBySkillIdAndStudentId(skill.getId(), studentId);

		if (evaluation.isPresent()) {
			if ("kno".equals(criteria) && evaluation.get().getKnowledge() != 0) {
				evaluation.get().setKnowledge(0);
				evaluationRepository.saveAndFlush(evaluation.get());
			} else if ("int".equals(criteria) && evaluation.get().getInterest() != 0) {
				evaluation.get().setInterest(0);
				evaluationRepository.saveAndFlush(evaluation.get());
			} else if ("exp".equals(criteria) && evaluation.get().getExperience() != 0) {
				evaluation.get().setExperience(0);
				evaluationRepository.saveAndFlush(evaluation.get());
			}

			if (evaluation.get().getInterest() == 0
				&& evaluation.get().getKnowledge() == 0
				&& evaluation.get().getExperience() == 0) {
				evaluationRepository.delete(evaluation.get());
			}
		}
	}

	public void reevaluateCriteriaForSkillAndStudent(String skillName, String criteria, Integer starsNumber, String studentId) {
		var skill = skillRepository.findByName(skillName);
		var evaluation = evaluationRepository.findBySkillIdAndStudentId(skill.getId(), studentId);

		if (evaluation.isPresent()) {
			if ("kno".equals(criteria)) {
				evaluation.get().setKnowledge(starsNumber);
				evaluationRepository.saveAndFlush(evaluation.get());
			} else if ("int".equals(criteria)) {
				evaluation.get().setInterest(starsNumber);
				evaluationRepository.saveAndFlush(evaluation.get());
			} else if ("exp".equals(criteria)) {
				evaluation.get().setExperience(starsNumber);
				evaluationRepository.saveAndFlush(evaluation.get());
			}
		} else {
			var newEvaluation = new Evaluation();
			newEvaluation.setStudentId(studentId);
			newEvaluation.setSkillId(skill.getId());
			newEvaluation.setKnowledge(0);
			newEvaluation.setInterest(0);
			newEvaluation.setExperience(0);

			if ("kno".equals(criteria)) {
				newEvaluation.setKnowledge(starsNumber);
				evaluationRepository.saveAndFlush(newEvaluation);
			} else if ("int".equals(criteria)) {
				newEvaluation.setInterest(starsNumber);
				evaluationRepository.saveAndFlush(newEvaluation);
			} else if ("exp".equals(criteria)) {
				newEvaluation.setExperience(starsNumber);
				evaluationRepository.saveAndFlush(newEvaluation);
			}
		}
	}

}
