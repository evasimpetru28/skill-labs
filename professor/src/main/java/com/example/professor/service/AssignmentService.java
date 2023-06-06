package com.example.professor.service;

import com.example.professor.entity.Assignment;
import com.example.professor.entity.Student;
import com.example.professor.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AssignmentService {

	final AssignmentRepository assignmentRepository;

	public List<Student> getAssignedStudentsOfQuiz(String quizId) {
		return assignmentRepository.findAssignedByQuizOrderByName(quizId);
	}

	public Assignment getAssignmentByStudentAndQuiz(String studentId, String quizId) {
		return assignmentRepository.findAssignmentByStudentIdAndQuizId(studentId, quizId);
	}

	public void deleteAssignment(Assignment assignment) {
		assignmentRepository.delete(assignment);
	}

	public void saveAssignment(Assignment assignment) {
		assignmentRepository.saveAndFlush(assignment);
	}
}
