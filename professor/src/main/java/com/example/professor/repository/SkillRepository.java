package com.example.professor.repository;

import com.example.professor.entity.Skill;
import com.example.professor.model.SelectOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SkillRepository extends JpaRepository<Skill, String> {

	@Query("""
	SELECT new com.example.professor.model.SelectOption(s.name || ' (' || c.name || ')', s.id, FALSE)
	FROM Skill s
	JOIN  Category c on c.id = s.categoryId
	""")
	List<SelectOption> findAllSkillAndCategorySelectOptions();

	@Query("""
	SELECT new com.example.professor.model.SelectOption(s.name || ' (' || c.name || ')', s.id,
		(CASE WHEN s.id = :selectedSkillId THEN TRUE ELSE FALSE END))
	FROM Skill s
	JOIN  Category c on c.id = s.categoryId
	""")
	List<SelectOption> findAllSkillAndCategorySelectOptionsAndSelected(String selectedSkillId);
}
