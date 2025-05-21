package com.example.professor.service;

import com.example.professor.dto.StudentDto;
import com.example.professor.entity.Assignment;
import com.example.professor.entity.Student;
import com.example.professor.repository.AssignmentRepository;
import com.example.professor.repository.ResponseRepository;
import com.example.professor.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@AllArgsConstructor
public class AssignmentService {

	final StudentRepository studentRepository;
	final ResponseRepository responseRepository;
	final AssignmentRepository assignmentRepository;

	public List<Student> getAssignedStudentsOfQuiz(String quizId) {
		return assignmentRepository.findAssignedByQuizOrderByName(quizId);
	}

	public Assignment getAssignmentByStudentAndQuiz(String studentId, String quizId) {
		return assignmentRepository.findAssignmentByStudentIdAndQuizId(studentId, quizId);
	}

	public List<StudentDto> getStudentInfoByQuizSubmitted(String quizId) {
		var index = new AtomicInteger(1);
		return assignmentRepository.findAllByQuizIdAndHasScore(quizId)
				.stream().map(assignment -> {
					var student = studentRepository.getReferenceById(assignment.getStudentId());
					var studentModel = new StudentDto();
					studentModel.setIndex(index.getAndIncrement());
					studentModel.setStudentId(assignment.getStudentId());
					studentModel.setStudentName(student.getName());
					studentModel.setStudentEmail(student.getEmail());
					studentModel.setStudentYear(student.getYear());
					studentModel.setStudentProgram(student.getProgram());
					studentModel.setStudentDomain(student.getDomain());
					studentModel.setScore(assignment.getScore());
					return studentModel;
				})
				.toList();
	}
	public List<StudentDto> getStudentInfoByQuiz(String quizId) {
		var index = new AtomicInteger(1);
		return assignmentRepository.findAllByQuizId(quizId)
				.stream().map(assignment -> {
					var student = studentRepository.getReferenceById(assignment.getStudentId());
					var studentModel = new StudentDto();
					studentModel.setIndex(index.getAndIncrement());
					studentModel.setStudentId(assignment.getStudentId());
					studentModel.setStudentName(student.getName());
					studentModel.setStudentEmail(student.getEmail());
					studentModel.setStudentYear(student.getYear());
					studentModel.setStudentProgram(student.getProgram());
					studentModel.setStudentDomain(student.getDomain());
					studentModel.setScore(assignment.getScore());
					return studentModel;
				})
				.toList();
	}

	public void deleteAssignment(Assignment assignment) {
		log.info("Delete all responses for assignment with id {}", assignment.getId());
		responseRepository.deleteAll(responseRepository.findAllByStudentIdAndQuizId(assignment.getStudentId(), assignment.getQuizId()));
		log.info("Delete assignment with id {}", assignment.getId());
		assignmentRepository.delete(assignment);
	}

	public void deleteAllAssignmentsForQuizId(String quizId) {
		assignmentRepository.deleteAll(getAllAssignmentsForQuiz(quizId));
	}

	public List<Assignment> getAllAssignmentsForQuiz(String quizId) {
		return assignmentRepository.findAllByQuizId(quizId);
	}

	public void saveAssignment(Assignment assignment) {
		assignmentRepository.saveAndFlush(assignment);
	}
}
