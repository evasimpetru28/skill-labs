package com.example.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectOption {
	String label;
	String value;
	Boolean selected;
}
