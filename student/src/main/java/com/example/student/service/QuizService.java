package com.example.student.service;

import com.example.student.entity.Quiz;
import com.example.student.model.QuizModel;
import com.example.student.repository.CategoryRepository;
import com.example.student.repository.QuizRepository;
import com.example.student.repository.SkillRepository;
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
	private final SkillRepository skillRepository;
	private final CategoryRepository categoryRepository;

	public Quiz getQuizById(String quizId) {
		return quizRepository.getReferenceById(quizId);
	}

	public String getQuizSkillLabel(String quizId) {
		var quiz = quizRepository.getReferenceById(quizId);
		var skill = skillRepository.getReferenceById(quiz.getSkillId());
		var category = categoryRepository.getReferenceById(skill.getCategoryId());
		return skill.getName() + " (" + category.getName() + ")";
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
					quizModel.setSkillLabel(quizInterface.getSkillName() + " (" + quizInterface.getCategoryName() + ")");
					quizModel.setScore(quizInterface.getScore());
					quizModel.setStatus(quizInterface.getIsExpired() ? "EXPIRED" : "ACTIVE");
					quizModel.setIsExpired(quizInterface.getIsExpired());
					quizModel.setCanReview(quizInterface.getScore() != null);
					quizModel.setCanAnswer(quizInterface.getScore() == null && !quizModel.getIsExpired());
					return quizModel;
				})
				.toList();
	}

}
