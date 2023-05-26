package com.example.student.service;

import com.example.student.entity.Skill;
import com.example.student.model.SkillModel;
import com.example.student.repository.CategoryRepository;
import com.example.student.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class SkillService {

	final SkillRepository skillRepository;
	final CategoryRepository categoryRepository;

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

	public List<SkillModel> getSkillModelList() {
		var skills = getAllSkills();
		var index = new AtomicInteger(1);
		return skills.stream()
				.map(skill -> new SkillModel(
						skill.getId(),
						index.getAndIncrement(),
						skill.getName(),
						skill.getDescription(),
						categoryRepository.findById(skill.getCategoryId()).get().getName(),
						!"".equals(skill.getDescription())))
				.toList();
	}

	public List<Skill> getAllSkills() {
		return skillRepository.findAllByOrderByNameAndCategoryName();
	}

}
