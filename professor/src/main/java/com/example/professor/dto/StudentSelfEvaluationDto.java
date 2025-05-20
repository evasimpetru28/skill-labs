package com.example.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentSelfEvaluationDto {
	Integer interest;
	Integer knowledge;
	Integer experience;
	String name;
	String email;
	Integer year;
	String program;
	String domain;
}
