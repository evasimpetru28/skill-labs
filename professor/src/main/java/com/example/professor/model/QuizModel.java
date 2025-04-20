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
public class QuizModel {

	String id;
	Integer index;
	String superuserId;
	String superuserName;
	String name;
	String description;
	boolean isPublic;
	String createdAt;
}
