package com.example.student.repository;

import com.example.student.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, String> {
	List<Option> findAllByQuestionIdOrderByCreatedAt(String questionId);
	@Query("select o.id from Option o where o.isCorrect = true and o.questionId = ?1 ")
	List<String> findAllByQuestionIdAndIsCorrectEqualsTrue(String questionId);
}
