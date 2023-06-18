package com.example.admin.service;

import com.example.admin.entity.Question;
import com.example.admin.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class QuestionService {

	final QuestionRepository questionRepository;

	public List<Question> getAllQuestionsByQuizId(String quizId) {
		return questionRepository.findAllByQuizIdOrderByCreatedAt(quizId);
	}

	public void deleteQuestionById(String questionId) {
		questionRepository.deleteById(questionId);
	}

}
