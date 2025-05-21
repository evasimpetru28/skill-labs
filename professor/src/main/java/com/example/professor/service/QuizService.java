package com.example.professor.service;

import com.example.professor.entity.Quiz;
import com.example.professor.dto.QuizDto;
import com.example.professor.repository.CategoryRepository;
import com.example.professor.repository.QuizRepository;
import com.example.professor.repository.SkillRepository;
import com.example.professor.repository.SuperuserRepository;
import com.example.professor.util.Utils;

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
	final SkillRepository skillRepository;
	final CategoryRepository categoryRepository;
	final SuperuserRepository superuserRepository;

	public void saveQuiz(Quiz quiz) {
		quizRepository.saveAndFlush(quiz);
	}

	public Quiz getQuizById(String quizId) {
		return quizRepository.getReferenceById(quizId);
	}

	public QuizDto getQuizDetailsById(String quizId) {
		return quizRepository.getQuizDtoById(quizId);
	}

	public List<QuizDto> getDraftedQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllBySuperuserIdNotReady(superuserId)
				.stream()
				.map(quiz -> new QuizDto(
							quiz.getId(),
							index.getAndIncrement(),
							quiz.getSuperuserId(),
							superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
							quiz.getName(),
							(quiz.getDescription() != null && quiz.getDescription().length() > 110)
									? quiz.getDescription().substring(0, 110) + "..."
									: quiz.getDescription(),
							"PUBLIC".equals(quiz.getStatus()),
							Utils.localDateTimeToString(quiz.getCreatedAt()),
							getSkillLabel(quiz.getSkillId()),
						quiz.getIsExpired()
				))
				.toList();
	}

	public String getSkillLabel(String skillId) {
		if (skillId == null) {
			return null;
		}
		var skill = skillRepository.getReferenceById(skillId);
		var category = categoryRepository.getReferenceById(skill.getCategoryId());
		return skill.getName() + " (" + category.getName() + ")";
	}

	public List<QuizDto> getAssignedQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllBySuperuserIdReady(superuserId)
				.stream()
				.map(quiz -> new QuizDto(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus()),
						Utils.localDateTimeToString(quiz.getCreatedAt()),
						getSkillLabel(quiz.getSkillId()),
						quiz.getIsExpired()
				))
				.toList();
	}

	public List<QuizDto> getExpiredQuizzesBySuperuserId(String superuserId) {
		var index = new AtomicInteger(1);
		return quizRepository.getAllExpiredBySuperuserId(superuserId)
				.stream()
				.map(quiz -> new QuizDto(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus()),
						Utils.localDateTimeToString(quiz.getCreatedAt()),
						getSkillLabel(quiz.getSkillId()),
						quiz.getIsExpired()
				))
				.toList();
	}

	public void deleteQuiz(String quizId) {
		quizRepository.deleteById(quizId);
	}

	public List<QuizDto> getPublicQuizzes() {
		var index = new AtomicInteger(1);
		return quizRepository.getAllPublicQuizzes()
				.stream()
				.map(quiz -> new QuizDto(
						quiz.getId(),
						index.getAndIncrement(),
						quiz.getSuperuserId(),
						superuserRepository.getReferenceById(quiz.getSuperuserId()).getName(),
						quiz.getName(),
						(quiz.getDescription() != null && quiz.getDescription().length() > 110)
								? quiz.getDescription().substring(0, 110) + "..."
								: quiz.getDescription(),
								"PUBLIC".equals(quiz.getStatus()),
						Utils.localDateTimeToString(quiz.getCreatedAt()),
						getSkillLabel(quiz.getSkillId()),
						quiz.getIsExpired()
				))
				.toList();
	}

	public List<QuizDto> getQuizListBySkillId(String skillId, String superuserId) {
		return quizRepository.findQuizDtoBySkillId(skillId, superuserId);
	}

}
