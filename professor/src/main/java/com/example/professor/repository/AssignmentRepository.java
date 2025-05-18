package com.example.professor.repository;

import com.example.professor.entity.Assignment;
import com.example.professor.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	@Query("select s from Student s join Assignment a on a.studentId = s.id and a.quizId = ?1 order by lower(s.name)")
	List<Student> findAssignedByQuizOrderByName(String quizId);

	Assignment findAssignmentByStudentIdAndQuizId(String studentId, String quizId);
	@Query("select a from Assignment a where a.quizId = ?1 and a.score is not null ")
	List<Assignment> findAllByQuizIdAndHasScore(String quizId);
	List<Assignment> findAllByQuizId(String quizId);

}
