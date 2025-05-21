package com.example.professor.service;

import com.example.professor.entity.Option;
import com.example.professor.entity.Question;
import com.example.professor.dto.OptionDto;
import com.example.professor.dto.QuestionDto;
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

	final ResponseService responseService;
	final OptionRepository optionRepository;
	final QuestionRepository questionRepository;

	public void saveQuestion(Question question) {
		questionRepository.saveAndFlush(question);
	}

	public Question getQuestionById(String questionId) {
		return questionRepository.getReferenceById(questionId);
	}

	public void addQuestionAndOption(String quizId) {
		var question = new Question();
		question.setQuizId(quizId);
		question.setQuestion("Untitled Question");
		saveQuestion(question);

		var option = new Option();
		option.setQuestionId(question.getId());
		option.setOptionText("Option 1");
		option.setIsCorrect(false);
		optionRepository.saveAndFlush(option);
	}

	public Map<QuestionDto, List<OptionDto>> getQuestionMap(String quizId) {
		var questions = getAllQuestionsByQuizId(quizId);
		var index = new AtomicInteger(1);
		return questions.stream()
				.map(question -> new QuestionDto(
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
									.map(option -> new OptionDto(
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

	public Map<QuestionDto, List<OptionDto>> getQuestionMapForStudent(String quizId, String studentId) {
		var questions = getAllQuestionsByQuizId(quizId);
		var index = new AtomicInteger(1);
		return questions.stream()
				.map(question -> new QuestionDto(
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
									.map(option -> new OptionDto(
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
