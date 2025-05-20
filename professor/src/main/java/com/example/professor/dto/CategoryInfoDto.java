package com.example.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {
    private String id;
    private String name;
    private String description;
    private boolean hasDescription;
} 