package com.example.admin.repository;

import com.example.admin.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
	List<Student> findAllByOrderByName();

	@Query("select s from Student s where lower(s.name) = lower(?1)")
	Optional<Student> existsByName(String name);

	@Query("select s from Student s where lower(s.name) = lower(?1) and s.id <> ?2")
	Optional<Student> existsByNameExcept(String name, String id);
}
