package com.example.student.repository;

import com.example.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
	Student findByResetCode(String resetCode);
	List<Student> findAllByOrderByName();
	Student findByEmail(String email);
}
