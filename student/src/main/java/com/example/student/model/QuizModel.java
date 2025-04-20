package com.example.student.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizModel {
	Integer index;
	String assignmentId;
	String quizId;
	String createdAt;
	String quizName;
	String description;
	String superuserId;
	String superuserName;
	String studentId;
	Integer score;
	String status;
	Boolean isExpired;
	Boolean canReview;
	Boolean canAnswer;
}
