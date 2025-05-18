package com.example.student.repository;

import com.example.student.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	Assignment findByStudentIdAndQuizId(String studentId, String quizId);

	@Query("""
	SELECT AVG(A.score)
	FROM Assignment A
	JOIN Quiz Q ON Q.id = A.quizId
	WHERE A.studentId = :studentId
	AND Q.skillId = :skillId
	AND A.score IS NOT NULL
	""")
	Integer getSkillScoreAverage(String studentId, String skillId);
}
