package com.example.student.service;

import com.example.student.entity.Quiz;
import com.example.student.model.QuizModel;
import com.example.student.repository.QuizRepository;
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

	public Quiz getQuizById(String quizId) {
		return quizRepository.getReferenceById(quizId);
	}

	public List<QuizModel> getQuizzesAssignedForStudent(String studentId) {
		var quizList = quizRepository.findQuizzesAssignedByStudentId(studentId);
		var index = new AtomicInteger(1);
		return quizList.stream()
				.map(quizInterface -> {
					var quizModel = new QuizModel();
					quizModel.setIndex(index.getAndIncrement());
					quizModel.setAssignmentId(quizInterface.getAssignmentId());
					quizModel.setQuizId(quizInterface.getQuizId());
					quizModel.setCreatedAt(quizInterface.getCreatedAt().substring(0,16));
					quizModel.setQuizName(quizInterface.getQuizName());
					quizModel.setDescription(quizInterface.getDescription());
					quizModel.setSuperuserId(quizInterface.getSuperuserId());
					quizModel.setSuperuserName(quizInterface.getSuperuserName());
					quizModel.setStudentId(quizInterface.getStudentId());
					quizModel.setScore(quizInterface.getScore());

					return quizModel;
				})
				.toList();
	}

}
