package com.example.professor.service;

import com.example.professor.entity.Option;
import com.example.professor.entity.Question;
import com.example.professor.model.OptionModel;
import com.example.professor.model.QuestionModel;
import com.example.professor.repository.OptionRepository;
import com.example.professor.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class QuestionService {

	final OptionRepository optionRepository;
	final QuestionRepository questionRepository;
	final ResponseService responseService;

	public void saveQuestion(Question question) {
		questionRepository.saveAndFlush(question);
	}

	public Question getQuestionById(String questionId) {
		return questionRepository.getReferenceById(questionId);
	}

	public Map<QuestionModel, List<OptionModel>> getQuestionMap(String quizId) {
		var questions = getAllQuestionsByQuizId(quizId);
		var index = new AtomicInteger(1);
		return questions.stream()
				.map(question -> new QuestionModel(
						question.getId(),
						index.getAndIncrement(),
						question.getQuestion(),
						question.getQuizId(),
						question.getCreatedAt()
				))
				.collect(Collectors.toMap(
						Function.identity(),
						question -> {
							var indexOption = new AtomicInteger(1);

							return optionRepository.findAllByQuestionIdOrderByCreatedAt(question.getId())
									.stream()
									.map(option -> new OptionModel(
											option.getId(),
											indexOption.getAndIncrement(),
											option.getOptionText(),
											option.getQuestionId(),
											null,
											option.getIsCorrect()
									))
									.toList();
						},
						(oldValue, newValue) -> oldValue,
						LinkedHashMap::new)
				);
	}

	public Map<QuestionModel, List<OptionModel>> getQuestionMapForStudent(String quizId, String studentId) {
		var questions = getAllQuestionsByQuizId(quizId);
		var index = new AtomicInteger(1);
		return questions.stream()
				.map(question -> new QuestionModel(
						question.getId(),
						index.getAndIncrement(),
						question.getQuestion(),
						question.getQuizId(),
						question.getCreatedAt()
				))
				.collect(Collectors.toMap(
						Function.identity(),
						question -> {
							var indexOption = new AtomicInteger(1);

							return optionRepository.findAllByQuestionIdOrderByCreatedAt(question.getId())
									.stream()
									.map(option -> new OptionModel(
											option.getId(),
											indexOption.getAndIncrement(),
											option.getOptionText(),
											option.getQuestionId(),
											getResponseIfExists(studentId, option),
											option.getIsCorrect()
									))
									.toList();
						},
						(oldValue, newValue) -> oldValue,
						LinkedHashMap::new)
				);
	}


	private String getResponseIfExists(String studentId, Option option) {
		return responseService.getResponseIfExistsForStudentAndOption(studentId, option.getId()).orElse(null);
	}

	public List<Question> getAllQuestionsByQuizId(String quizId) {
		return questionRepository.findAllByQuizIdOrderByCreatedAt(quizId);
	}

	public void deleteQuestionById(String questionId) {
		questionRepository.deleteById(questionId);
	}

}
