package com.example.professor.service;

import com.example.professor.entity.Assignment;
import com.example.professor.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssignmentService {

	final AssignmentRepository assignmentRepository;

	public void saveAssignment(Assignment assignment) {
		assignmentRepository.saveAndFlush(assignment);
	}
}
