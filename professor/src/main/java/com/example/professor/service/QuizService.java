package com.example.professor.service;

import com.example.professor.entity.Quiz;
import com.example.professor.entity.Superuser;
import com.example.professor.model.QuestionModel;
import com.example.professor.model.QuizModel;
import com.example.professor.model.SuperuserModel;
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

}
