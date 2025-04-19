package com.example.admin.repository;

import com.example.admin.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, String> {
	@Query("select s from Skill s, Category c where s.categoryId = c.id order by c.name, s.name")
	List<Skill> findAllByOrderByNameAndCategoryName();

	@Query("select (count(s) > 0) from Skill s where lower(s.name) = lower(?1)")
	boolean existsByNameIgnoreCase(String name);

	@Query("select (count(s) > 0) from Skill s where lower(s.name) = lower(?1) and s.id <> ?2")
	boolean existsByNameIgnoreCase(String name, String id);

	@Query("select s from Skill s where lower(s.name) = lower(?1)")
	Optional<Skill> findByNameIgnoreCase(String name);
}
