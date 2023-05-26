package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.model.StudentModel;
import com.example.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class StudentService {

	final StudentRepository studentRepository;

	public void saveStudent(Student student) {
		studentRepository.saveAndFlush(student);
	}

	public Student getStudentByEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	public void deleteStudent(String studentId) {
		studentRepository.deleteById(studentId);
	}

	public Boolean isDuplicate(String name) {
		return studentRepository.existsByName(name).isPresent();
	}

	public Boolean isDuplicateExcept(String name, String id) {
		return studentRepository.existsByNameExcept(name, id).isPresent();
	}

	public Student getStudentByResetCode(String resetCode) {
		return studentRepository.findByResetCode(resetCode);
	}

	public List<StudentModel> getStudentModelList() {
		var students = getAllStudents();
		var index = new AtomicInteger(1);
		return students.stream()
				.map(student -> new StudentModel(
						student.getId(),
						index.getAndIncrement(),
						student.getName(),
						student.getEmail(),
						student.getPhone(),
						student.getYear(),
						student.getProgram(),
						student.getDomain()
				))
				.toList();
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAllByOrderByName();
	}

	public Student getStudentById(String id) {
		return studentRepository.getReferenceById(id);
	}

}
