package com.example.professor.dto;

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
