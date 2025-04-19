package com.example.admin.service;

import com.example.admin.entity.Skill;
import com.example.admin.model.SkillModel;
import com.example.admin.repository.CategoryRepository;
import com.example.admin.repository.SkillRepository;
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
		return skillRepository.existsByNameIgnoreCase(name);
	}

	public Boolean isDuplicateExcept(String name, String id) {
		return skillRepository.existsByNameIgnoreCase(name, id);
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
						categoryRepository.getReferenceById(skill.getCategoryId()).getName(),
						skill.getCategoryId(),
						!"".equals(skill.getDescription())))
				.toList();
	}

	public List<Skill> getAllSkills() {
		return skillRepository.findAllByOrderByNameAndCategoryName();
	}

	public Skill getSkillByName(String name) {
		return skillRepository.findByNameIgnoreCase(name).orElse(null);
	}

}
