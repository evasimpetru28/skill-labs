package com.example.professor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SkillDetailsDto {
	String id;
	String name;
	String description;
	String categoryName;
}
