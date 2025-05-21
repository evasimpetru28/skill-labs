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
public class QuizDto {

	String id;
	Integer index;
	String superuserId;
	String superuserName;
	String name;
	String description;
	boolean isPublic;
	String createdAt;
	String skillLabel;
	Boolean isExpired;
}
