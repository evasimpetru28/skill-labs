package com.example.admin.repository;

import com.example.admin.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	void deleteAllByStudentId(String studentId);
}
