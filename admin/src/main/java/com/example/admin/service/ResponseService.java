package com.example.admin.service;

import com.example.admin.repository.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ResponseService {

	final ResponseRepository responseRepository;

	public void deleteAllResponsesForStudent(String studentId) {
		responseRepository.deleteAllByStudentId(studentId);
	}
}
