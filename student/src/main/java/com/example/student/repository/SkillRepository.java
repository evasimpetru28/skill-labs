package com.example.student.repository;

import com.example.student.entity.Skill;
import com.example.student.model.SkillInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, String> {
	@Query("""
	select s.id as id,
			s.name as name,
			s.description as description,
			c.name as category
	from Skill s
	left join Category c on s.categoryId = c.id
	order by lower(c.name), lower(s.name)
	""")
	List<SkillInterface> findAlSkillsAndCategories();

}
