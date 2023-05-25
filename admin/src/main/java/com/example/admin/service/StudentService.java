package com.example.admin.service;

import com.example.admin.entity.Student;
import com.example.admin.model.StudentModel;
import com.example.admin.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class StudentService {

	final StudentRepository studentRepository;

	public void saveStudent(Student student) {
		studentRepository.saveAndFlush(student);
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
