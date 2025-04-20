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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

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

	public Map<String, List<SkillEvaluationModel>> getAllSkillsAndEvaluationsForStudent(String studentId) {
		var skills = skillRepository.findAlSkillsAndCategories();
		var index = new AtomicInteger(1);
		return skills.stream()
				.map(skill -> {
					var optionalSkill = evaluationRepository.findBySkillIdAndStudentId(skill.getId(), studentId);
					var skillModel = new SkillEvaluationModel();
					skillModel.setIndex(index.getAndIncrement());
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
				.collect(Collectors.groupingBy(
					SkillEvaluationModel::getCategory,
					LinkedHashMap::new,
					Collectors.toList()
				));
	}

	public List<SkillEvaluationModel> getEvaluationsForStudent(String studentId) {
		var evaluations = evaluationRepository.findAllEvaluationsByStudentId(studentId);
		var index = new AtomicInteger(1);
		return evaluations.stream()
				.map(skill -> {
					var skillModel = new SkillEvaluationModel();
					skillModel.setIndex(index.getAndIncrement());
					skillModel.setId(skill.getId());
					skillModel.setName(skill.getName());
					skillModel.setDescription(skill.getDescription());
					skillModel.setCategory(skill.getCategory());
					skillModel.setHasEvaluation(skill.getHasEvaluation());
					skillModel.setHasDescription(skill.getHasDescription());
					skillModel.setInterest(skill.getInterest());
					skillModel.setKnowledge(skill.getKnowledge());
					skillModel.setExperience(skill.getExperience());

					return skillModel;
				})
				.toList();
	}
}
