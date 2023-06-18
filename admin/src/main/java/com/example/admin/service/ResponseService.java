package com.example.admin.service;

import com.example.admin.entity.Response;
import com.example.admin.repository.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ResponseService {

	final ResponseRepository responseRepository;

	public void deleteAllResponsesForStudent(String studentId) {
		responseRepository.deleteAllByStudentId(studentId);
	}

	public void deleteAllResponsesByQuestionId(String questionId) {
		responseRepository.deleteAll(getAllResponsesByQuestionId(questionId));
	}

	public List<Response> getAllResponsesByQuestionId(String questionId) {
		return responseRepository.findAllByQuestionId(questionId);
	}

}
