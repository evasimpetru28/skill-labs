package com.example.student.model;

import java.time.LocalDateTime;

public interface QuizInterface {
	String getAssignmentId();
	String getQuizId();
	LocalDateTime getCreatedAt();
	String getQuizName();
	String getDescription();
	String getSuperuserId();
	String getSuperuserName();
	String getStudentId();
	Integer getScore();
	String getStatus();
	Boolean getIsExpired();
}
