package com.example.admin.repository;

import com.example.admin.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	void deleteAllByStudentId(String studentId);
	List<Assignment> findAllByQuizId(String quizId);
}
