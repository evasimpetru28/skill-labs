package com.example.student.service;

import com.example.student.entity.Skill;
import com.example.student.model.SkillEvaluationModel;
import com.example.student.repository.CategoryRepository;
import com.example.student.repository.EvaluationRepository;
import com.example.student.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SkillService {

	final SkillRepository skillRepository;
	final CategoryRepository categoryRepository;
	final EvaluationRepository evaluationRepository;

	public void saveSkill(Skill skill) {
		skillRepository.saveAndFlush(skill);
	}

	public void deleteSkill(String skillId) {
		skillRepository.deleteById(skillId);
	}

	public Boolean isDuplicate(String name) {
		return skillRepository.existsByName(name).isPresent();
	}

	public Boolean isDuplicateExcept(String name, String id) {
		return skillRepository.existsByNameExcept(name, id).isPresent();
	}

	public List<SkillEvaluationModel> getAllSkillsAndEvaluationsForStudent(String studentId) {
		var skills = skillRepository.findAlSkillsAndCategories();
		return skills.stream()
				.map(skill -> {
					var optionalSkill = evaluationRepository.findBySkillIdAndStudentId(skill.getId(), studentId);
					var skillModel = new SkillEvaluationModel();
					skillModel.setId(skill.getId());
					skillModel.setName(skill.getName());
					skillModel.setDescription(skill.getDescription());
					skillModel.setHasDescription(!"".equals(skill.getDescription()));
					skillModel.setCategory(skill.getCategory());
					skillModel.setHasEvaluation(false);
					if (optionalSkill.isPresent()) {
						skillModel.setInterest(optionalSkill.get().getInterest());
						skillModel.setKnowledge(optionalSkill.get().getKnowledge());
						skillModel.setExperience(optionalSkill.get().getExperience());
						skillModel.setHasEvaluation(true);
					}
					return skillModel;
				})
				.toList();
	}

	public List<SkillEvaluationModel> getEvaluationsForStudent(String studentId) {
		return evaluationRepository.findAllEvaluationsByStudentId(studentId);
	}

}
