package com.example.student.repository;

import com.example.student.entity.Skill;
import com.example.student.model.SkillInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, String> {
	@Query(value = """
					select s.id as id,
							s.name as name, 
							s.description as description, 
							c.name as category
					from skill s
					left join category c on s.category_id = c.id
					order by lower(c.name), lower(s.name)
			""", nativeQuery = true)
	List<SkillInterface> findAlSkillsAndCategories();
	@Query("select s from Skill s where lower(s.name) = lower(?1)")
	Optional<Skill> existsByName(String name);
	@Query("select s from Skill s where lower(s.name) = lower(?1) and s.id <> ?2")
	Optional<Skill> existsByNameExcept(String name, String id);
	Skill findByName(String name);
}
