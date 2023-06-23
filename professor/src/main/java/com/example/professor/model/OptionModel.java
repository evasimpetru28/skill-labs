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
public class OptionModel {

	String id;
	Integer index;
	String optionText;
	String questionId;
	String responseId;
	Boolean isCorrect;
}
