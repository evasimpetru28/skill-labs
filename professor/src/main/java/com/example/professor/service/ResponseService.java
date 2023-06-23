package com.example.professor.service;

import com.example.professor.entity.Response;
import com.example.professor.repository.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ResponseService {

	final ResponseRepository responseRepository;

	public Optional<String> getResponseIfExistsForStudentAndOption(String studentId, String optionId) {
		return responseRepository.findByStudentIdAndOptionId(studentId, optionId);
	}

	public void deleteAllResponsesByQuestionId(String questionId) {
		responseRepository.deleteAll(getAllResponsesByQuestionId(questionId));
	}

	public List<Response> getAllResponsesByQuestionId(String questionId) {
		return responseRepository.findAllByQuestionId(questionId);
	}

}
