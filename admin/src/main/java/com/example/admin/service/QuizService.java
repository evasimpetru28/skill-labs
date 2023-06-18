package com.example.admin.service;

import com.example.admin.entity.Quiz;
import com.example.admin.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class QuizService {

	final QuizRepository quizRepository;

	public List<Quiz> getAllBySuperuser(String superuserId) {
		return quizRepository.findAllBySuperuserId(superuserId);
	}

	public void deleteQuiz(String quizId) {
		quizRepository.deleteById(quizId);
	}

}
