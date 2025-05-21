package com.example.professor.repository;

import com.example.professor.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, String> {

	List<Response> findAllByQuestionId(String questionId);
	@Query("select r.id from Response r where r.studentId = ?1 and r.optionId = ?2")
	Optional<String> findByStudentIdAndOptionId(String studentId, String optionId);
	@Query("""
	SELECT r
	FROM Response r
	JOIN Question q ON q.id = r.questionId
	WHERE q.quizId = :quizId
	AND r.studentId = :studentId
	""")
	List<Response> findAllByStudentIdAndQuizId(String studentId, String quizId);

}
