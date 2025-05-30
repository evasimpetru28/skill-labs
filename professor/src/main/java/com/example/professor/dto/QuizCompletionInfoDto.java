package com.example.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class QuizCompletionInfoDto {
	BigDecimal completionRate;
	long completedQuizzesStudents;
	long completionTotalStudents;
}
