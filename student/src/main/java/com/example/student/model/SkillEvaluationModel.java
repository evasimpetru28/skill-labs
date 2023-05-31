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
	Integer index;
	String id;
	String name;
	String description;
	String category;
	Boolean hasEvaluation;
	Boolean hasDescription;
	Integer interest;
	Integer knowledge;
	Integer experience;
}
