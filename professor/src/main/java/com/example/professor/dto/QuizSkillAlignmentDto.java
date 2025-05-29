package com.example.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class QuizSkillAlignmentDto {
	BigDecimal quizSkillAlignment;
	long quizSkillAlignmentCount;
	long quizSkillAlignmentTotal;
}
