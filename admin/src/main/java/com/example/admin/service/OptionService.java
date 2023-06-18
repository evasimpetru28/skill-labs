package com.example.admin.service;

import com.example.admin.repository.OptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OptionService {

	final OptionRepository optionRepository;

	public void deleteOptionsOfQuestion(String questionId) {
		optionRepository.deleteAll(optionRepository.findAllByQuestionIdOrderByCreatedAt(questionId));
	}

}
