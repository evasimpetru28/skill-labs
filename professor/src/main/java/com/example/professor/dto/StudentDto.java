package com.example.professor.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {
	Integer index;
	String studentId;
	String studentName;
	String studentEmail;
	Integer studentYear;
	String studentProgram;
	String studentDomain;
	Integer score;
}
