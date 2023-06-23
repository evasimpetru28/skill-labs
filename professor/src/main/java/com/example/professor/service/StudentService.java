package com.example.professor.service;

import com.example.professor.entity.Student;
import com.example.professor.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class StudentService {

	final StudentRepository studentRepository;

	public List<String> getAllStudentsNotAssignedByQuiz(String quizId) {
		return studentRepository.findAllNotAssignedByQuizOrderByName(quizId);
	}

	public Student getStudentByName(String name) {
		return studentRepository.findStudentByName(name);
	}

	public Student getStudentById(String studentId) {
		return studentRepository.getReferenceById(studentId);
	}

}
