package com.example.professor.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentModel {
	Integer index;
	String studentId;
	String studentName;
	String studentEmail;
	Integer studentYear;
	String studentProgram;
	String studentDomain;
	Integer score;
}
