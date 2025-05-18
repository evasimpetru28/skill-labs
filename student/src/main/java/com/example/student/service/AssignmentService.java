package com.example.student.service;

import com.example.student.entity.Assignment;
import com.example.student.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AssignmentService {

	final AssignmentRepository assignmentRepository;

	public void saveAssignment(Assignment assignment) {
		assignmentRepository.saveAndFlush(assignment);
	}

	public Assignment getAssignmentByStudentAndQuiz(String studentId, String quizId) {
		return assignmentRepository.findByStudentIdAndQuizId(studentId, quizId);
	}

	public Integer getAssignmentScoreAverageBySkillForStudent(String studentId, String skillId) {
		return assignmentRepository.getSkillScoreAverage(studentId, skillId);
	}
}
