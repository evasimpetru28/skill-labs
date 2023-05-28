package com.example.admin.service;

import com.example.admin.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AssignmentService {

	final AssignmentRepository assignmentRepository;

	public void deleteAllAssignmentsForStudent(String studentId) {

	}
}
