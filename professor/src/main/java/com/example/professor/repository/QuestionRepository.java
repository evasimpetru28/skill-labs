package com.example.professor.repository;

import com.example.professor.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {
	List<Question> findAllByQuizIdOrderByCreatedAt(String quizId);
}
