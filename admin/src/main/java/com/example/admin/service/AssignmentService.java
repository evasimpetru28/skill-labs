package com.example.admin.service;

import com.example.admin.entity.Assignment;
import com.example.admin.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AssignmentService {

	final AssignmentRepository assignmentRepository;

	public void deleteAllAssignmentsForStudent(String studentId) {
		assignmentRepository.deleteAllByStudentId(studentId);
	}

	public void deleteAllAssignmentsForQuizId(String quizId) {
		assignmentRepository.deleteAll(getAllAssignmentsForQuiz(quizId));
	}

	public List<Assignment> getAllAssignmentsForQuiz(String quizId) {
		return assignmentRepository.findAllByQuizId(quizId);
	}
}
