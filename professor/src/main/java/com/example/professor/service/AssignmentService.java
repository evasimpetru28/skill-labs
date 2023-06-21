package com.example.professor.service;

import com.example.professor.entity.Assignment;
import com.example.professor.entity.Student;
import com.example.professor.model.StudentModel;
import com.example.professor.repository.AssignmentRepository;
import com.example.professor.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class AssignmentService {

	final AssignmentRepository assignmentRepository;
	final StudentRepository studentRepository;

	public List<Student> getAssignedStudentsOfQuiz(String quizId) {
		return assignmentRepository.findAssignedByQuizOrderByName(quizId);
	}

	public Assignment getAssignmentByStudentAndQuiz(String studentId, String quizId) {
		return assignmentRepository.findAssignmentByStudentIdAndQuizId(studentId, quizId);
	}

	public List<StudentModel> getStudentInfoByQuizSubmitted(String quizId) {
		var index = new AtomicInteger(1);
		return assignmentRepository.findAllByQuizIdAndHasScore(quizId)
				.stream().map(assignment -> {
					var student = studentRepository.getReferenceById(assignment.getStudentId());
					var studentModel = new StudentModel();
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
	public List<StudentModel> getStudentInfoByQuizNotSubmitted(String quizId) {
		var index = new AtomicInteger(0);
		return assignmentRepository.findAllByQuizIdAndWithoutScore(quizId)
				.stream().map(assignment -> {
					var student = studentRepository.getReferenceById(assignment.getStudentId());
					var studentModel = new StudentModel();
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
