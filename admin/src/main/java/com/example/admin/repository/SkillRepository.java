package com.example.admin.repository;

import com.example.admin.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, String> {
	@Query("select s from Skill s, Category c where s.categoryId = c.id order by c.name, s.name")
	List<Skill> findAllByOrderByNameAndCategoryName();
	@Query("select s from Skill s where lower(s.name) = lower(?1)")
	Optional<Skill> existsByName(String name);
	@Query("select s from Skill s where lower(s.name) = lower(?1) and s.id <> ?2")
	Optional<Skill> existsByNameExcept(String name, String id);
}
