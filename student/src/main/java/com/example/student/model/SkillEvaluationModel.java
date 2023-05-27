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
public class SkillEvaluationModel {
	String id;
	String name;
	String description;
	String category;
	Boolean hasEvaluation;
	Integer interest;
	Integer knowledge;
	Integer experience;
}
