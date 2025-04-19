package com.example.student.service;

import com.example.student.entity.Response;
import com.example.student.repository.OptionRepository;
import com.example.student.repository.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class ResponseService {

	final ResponseRepository responseRepository;
	final OptionRepository optionRepository;

	public Response getResponseById(String responseId) {
		return responseRepository.getReferenceById(responseId);
	}

	public void saveResponse(Response response) {
		responseRepository.saveAndFlush(response);
	}

	public void deleteResponseById(String responseId) {
		responseRepository.deleteById(responseId);
	}

	public Optional<String> getResponseIfExistsForStudentAndOption(String studentId, String optionId) {
		return responseRepository.findByStudentIdAndOptionId(studentId, optionId);
	}

	public Integer getAnsweredQuestionsForStudent(String studentId, List<String> questionIdList) {
		return responseRepository.getAnsweredQuestionsByStudentIdAndQuestionIdInList(studentId, questionIdList).size();
	}

	public Integer calculateScore(List<String> questionIdList, String studentId) {
		AtomicInteger score = new AtomicInteger(0);
		questionIdList.forEach(questionId -> {
			var correctOptions = optionRepository.findAllByQuestionIdAndIsCorrectEqualsTrue(questionId);
			var selectedOptions = responseRepository.getSelectedOptionsByStudentIdAndQuestionId(studentId, questionId);

			if (correctOptions.equals(selectedOptions)) {
				score.getAndIncrement();
			}
		});
		return 100 * score.get() / questionIdList.size();
	}
}
