package com.example.student.service;

import com.example.student.entity.Response;
import com.example.student.repository.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ResponseService {

	final ResponseRepository responseRepository;

	public void saveResponse(Response response) {
		responseRepository.saveAndFlush(response);
	}

	public void deleteResponseById(String responseId) {
		responseRepository.deleteById(responseId);
	}

	public Optional<String> getResponseIfExistsForStudentAndOption(String studentId, String optionId) {
		return responseRepository.findByStudentIdAndOptionId(studentId, optionId);
	}

}
