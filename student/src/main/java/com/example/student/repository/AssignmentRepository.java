package com.example.student.repository;

import com.example.student.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	Assignment findByStudentIdAndQuizId(String studentId, String quizId);
}
