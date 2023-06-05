package com.example.professor.repository;

import com.example.professor.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
	@Query("select s from Student s order by lower(s.name)")
	List<Student> findAllOrderByName();
}
