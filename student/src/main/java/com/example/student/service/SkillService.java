package com.example.student.service;

import com.example.student.entity.Evaluation;
import com.example.student.entity.Skill;
import com.example.student.entity.Category;
import com.example.student.model.CategoryInfo;
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
import java.util.HashMap;
import java.util.TreeMap;

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

	public Map<CategoryInfo, List<SkillEvaluationModel>> getAllSkillsAndEvaluationsForStudent(String studentId) {
		var skills = skillRepository.findAlSkillsAndCategories();
		var evaluationsMap = evaluationRepository.findAllEvaluationsByStudentId(studentId)
				.stream()
				.collect(Collectors.toMap(
					eval -> eval.getId(),
					eval -> eval
				));
		var categoriesMap = categoryRepository.findAll()
				.stream()
				.collect(Collectors.toMap(
					Category::getName,
					category -> category
				));
		var categoryIndices = new HashMap<String, Integer>();
		var nextCategoryIndex = new AtomicInteger(1);
		var skillIndex = new AtomicInteger(1);
		return skills.stream()
				.map(skill -> {
					var skillModel = new SkillEvaluationModel();
					skillModel.setIndex(skillIndex.getAndIncrement());
					skillModel.setId(skill.getId());
					skillModel.setName(skill.getName());
					skillModel.setDescription(skill.getDescription());
					skillModel.setHasDescription(!"".equals(skill.getDescription()));
					skillModel.setCategory(skill.getCategory());
					skillModel.setHasEvaluation(false);

					var evaluation = evaluationsMap.get(skill.getId());
					if (evaluation != null) {
						skillModel.setInterest(evaluation.getInterest());
						skillModel.setKnowledge(evaluation.getKnowledge());
						skillModel.setExperience(evaluation.getExperience());
						skillModel.setHasEvaluation(true);
					}

					return skillModel;
				})
				.collect(Collectors.groupingBy(
					skill -> {
						var categoryName = skill.getCategory();
						var categoryIndex = categoryIndices.computeIfAbsent(
							categoryName,
							k -> nextCategoryIndex.getAndIncrement()
						);
						var category = categoriesMap.get(categoryName);
						
						return new CategoryInfo(
							categoryIndex,
							categoryName,
							category != null ? category.getDescription() : "",
							category != null && !"".equals(category.getDescription())
						);
					},
					LinkedHashMap::new,
					Collectors.toList()
				));
	}

	public Map<CategoryInfo, List<SkillEvaluationModel>> getEvaluationsForStudentByCategory(String studentId) {
		var evaluations = evaluationRepository.findAllEvaluationsByStudentId(studentId)
				.stream()
				.collect(Collectors.toMap(
					eval -> eval.getId(),
					eval -> eval
				));
		var categoriesMap = categoryRepository.findAll()
				.stream()
				.collect(Collectors.toMap(
					Category::getName,
					category -> category,
					(e1, e2) -> e1,
					TreeMap::new
				));
		var categoryIndices = new HashMap<String, Integer>();
		var nextCategoryIndex = new AtomicInteger(1);
		var skillIndex = new AtomicInteger(1);

		return evaluations.entrySet().stream()
				.map(skill -> {
					var skillModel = new SkillEvaluationModel();
					skillModel.setIndex(skillIndex.getAndIncrement());
					skillModel.setId(skill.getValue().getId());
					skillModel.setName(skill.getValue().getName());
					skillModel.setDescription(skill.getValue().getDescription());
					skillModel.setCategory(skill.getValue().getCategory());
					skillModel.setHasEvaluation(skill.getValue().getHasEvaluation());
					skillModel.setHasDescription(skill.getValue().getHasDescription());
					skillModel.setInterest(skill.getValue().getInterest());
					skillModel.setKnowledge(skill.getValue().getKnowledge());
					skillModel.setExperience(skill.getValue().getExperience());

					return skillModel;
				})
				.sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()))
				.collect(Collectors.groupingBy(
					skill -> {
						var categoryName = skill.getCategory();
						var categoryIndex = categoryIndices.computeIfAbsent(
							categoryName,
							k -> nextCategoryIndex.getAndIncrement()
						);
						var category = categoriesMap.get(categoryName);
						
						return new CategoryInfo(
							categoryIndex,
							categoryName,
							category != null ? category.getDescription() : "",
							category != null && !"".equals(category.getDescription())
						);
					},
					() -> new TreeMap<>((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName())),
					Collectors.toList()
				));
	}
}
