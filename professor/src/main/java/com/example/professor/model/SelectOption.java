package com.example.professor.model;

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
