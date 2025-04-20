package com.example.student.service;

import com.example.student.entity.Quiz;
import com.example.student.model.QuizModel;
import com.example.student.repository.QuizRepository;
import com.example.student.util.Utils;

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
					quizModel.setCreatedAt(Utils.localDateTimeToString(quizInterface.getCreatedAt()));
					quizModel.setQuizName(quizInterface.getQuizName());
					quizModel.setDescription(
							(quizInterface.getDescription() != null && quizInterface.getDescription().length() > 55)
									? quizInterface.getDescription().substring(0, 55) + "..."
									: quizInterface.getDescription()
					);
					quizModel.setSuperuserId(quizInterface.getSuperuserId());
					quizModel.setSuperuserName(quizInterface.getSuperuserName());
					quizModel.setStudentId(quizInterface.getStudentId());
					quizModel.setScore(quizInterface.getScore());
					quizModel.setStatus("EXPIRED".equals(quizInterface.getStatus()) ? "EXPIRED" : "ACTIVE");
					quizModel.setIsExpired("EXPIRED".equals(quizInterface.getStatus()));
					quizModel.setCanReview(quizInterface.getScore() != null);
					quizModel.setCanAnswer(quizInterface.getScore() == null && !quizModel.getIsExpired());
					return quizModel;
				})
				.toList();
	}

}
