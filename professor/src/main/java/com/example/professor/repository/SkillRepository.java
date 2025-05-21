package com.example.professor.repository;

import com.example.professor.entity.Skill;
import com.example.professor.dto.SelectOption;
import com.example.professor.dto.SkillDetailsDto;
import com.example.professor.dto.SkillInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SkillRepository extends JpaRepository<Skill, String> {

	@Query("""
	SELECT new com.example.professor.dto.SelectOption(s.name || ' (' || c.name || ')', s.id, FALSE)
	FROM Skill s
	JOIN  Category c on c.id = s.categoryId
	""")
	List<SelectOption> findAllSkillAndCategorySelectOptions();

	@Query("""
	SELECT new com.example.professor.dto.SelectOption(s.name || ' (' || c.name || ')', s.id,
		(CASE WHEN s.id = :selectedSkillId THEN TRUE ELSE FALSE END))
	FROM Skill s
	JOIN  Category c on c.id = s.categoryId
	""")
	List<SelectOption> findAllSkillAndCategorySelectOptionsAndSelected(String selectedSkillId);

	@Query("""
	SELECT new com.example.professor.dto.SkillInfoDto(s.id, s.name, s.description, s.categoryId,
		CASE WHEN s.description = '' THEN FALSE ELSE TRUE END, CASE WHEN bss.id IS NULL THEN FALSE ELSE TRUE END)
	FROM Skill s
	LEFT JOIN BookmarkedSuperuserSkill bss ON bss.skillId = s.id AND bss.superuserId = :superuserId
	WHERE s.categoryId = :categoryId
	""")
	List<SkillInfoDto> findByCategoryId(String categoryId, String superuserId);

	@Query("""
	SELECT new com.example.professor.dto.SkillInfoDto(s.id, s.name, s.description, s.categoryId,
		CASE WHEN s.description = '' THEN FALSE ELSE TRUE END, CASE WHEN bss.id IS NULL THEN FALSE ELSE TRUE END)
	FROM Skill s
	JOIN BookmarkedSuperuserSkill bss ON bss.skillId = s.id AND bss.superuserId = :superuserId
	WHERE s.categoryId = :categoryId
	""")
	List<SkillInfoDto> findBookmarkedByCategoryId(String categoryId, String superuserId);

	@Query("""
	SELECT new com.example.professor.dto.SkillDetailsDto(s.id, s.name, s.description, c.name, CASE WHEN bss.id IS NULL THEN FALSE ELSE TRUE END)
	FROM Skill s
	JOIN Category c on c.id = s.categoryId
	LEFT JOIN BookmarkedSuperuserSkill  bss ON bss.skillId = s.id AND bss.superuserId = :superuserId
	WHERE s.id = :skillId
	""")
	SkillDetailsDto getSkillDetailsById(String skillId, String superuserId);
}
