package com.example.student.repository;

import com.example.student.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {
	List<Question> findAllByQuizIdOrderByCreatedAt(String quizId);
	@Query("select q.id from Question  q where q.quizId = ?1 ")
	List<String> findAllQuestionIdsByQuizId(String quizId);
	@Query(value = "select count(*) from question q where q.quiz_id = ?1 ", nativeQuery = true)
	Integer getCountByQuizId(String quizId);
}
