package com.example.professor.repository;

import com.example.professor.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
	@Query("select concat(concat('\"', s.name), '\"') from Student s where s.id not in (select a.studentId from Assignment a where a.quizId = ?1) order by lower(s.name)")
	List<String> findAllNotAssignedByQuizOrderByName(String quizId);
	Student findStudentByName(String name);
}
