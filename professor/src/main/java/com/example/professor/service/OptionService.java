package com.example.professor.service;

import com.example.professor.entity.Option;
import com.example.professor.repository.OptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OptionService {

	final OptionRepository optionRepository;

	public void saveOption(Option option) {
		optionRepository.saveAndFlush(option);
	}

	public Option getOptionById(String optionId) {
		return optionRepository.getReferenceById(optionId);
	}

	public void deleteOptionsOfQuestion(String questionId) {
		optionRepository.deleteAll(optionRepository.findAllByQuestionIdOrderByCreatedAt(questionId));
	}

	public void deleteOptionById(String optionId) {
		optionRepository.deleteById(optionId);
	}

	public Integer getOptionNumberOfQuestion(String questionId) {
		return optionRepository.getOptionNumberByQuestionId(questionId);
	}
}
