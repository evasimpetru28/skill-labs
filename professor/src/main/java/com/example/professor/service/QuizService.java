package com.example.professor.service;

import com.example.professor.entity.Quiz;
import com.example.professor.model.QuizModel;
import com.example.professor.repository.QuizRepository;
import com.example.professor.repository.SuperuserRepository;
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
	final SuperuserRepository superuserRepository;

	public void saveQuiz(Quiz quiz) {
		quizRepository.saveAndFlush(quiz);
	}

	public Quiz getQuizById(String quizId) {
		return quizRepository.getReferenceById(quizId);
	}

	public List<QuizModel> getDraftedQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllBySuperuserIdNotReady(superuserId)
				.stream()
				.map(quiz -> new QuizModel(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus())
				))
				.toList();
	}

	public List<QuizModel> getAssignedQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllBySuperuserIdReady(superuserId)
				.stream()
				.map(quiz -> new QuizModel(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus())
				))
				.toList();
	}

	public List<QuizModel> getExpiredQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllExpiredBySuperuserId(superuserId)
				.stream()
				.map(quiz -> new QuizModel(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus())
				))
				.toList();
	}

	public void deleteQuiz(String quizId) {
		quizRepository.deleteById(quizId);
	}

	public List<QuizModel> getPublicQuizzes() {
		var index = new AtomicInteger(1);
		return quizRepository.getAllPublicQuizzes()
				.stream()
				.map(quiz -> new QuizModel(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus())
				))
				.toList();
	}

}
