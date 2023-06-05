package com.example.professor.service;

import com.example.professor.entity.Quiz;
import com.example.professor.model.QuizModel;
import com.example.professor.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class QuizService {

	final QuizRepository quizRepository;

	public void saveQuiz(Quiz quiz) {
		quizRepository.saveAndFlush(quiz);
	}

	public Quiz getQuizById(String quizId) {
		return quizRepository.getReferenceById(quizId);
	}

	public List<QuizModel> getDraftedQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllBySuperuserIdNotAssigned(superuserId)
				.stream()
				.map(quiz -> new QuizModel(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 100)
								? quiz.getDescription().substring(0, 100) + "..."
								: quiz.getDescription()
				))
				.toList();
	}

}
