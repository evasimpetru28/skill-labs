package com.example.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfo {
    private Integer index;
    private String name;
    private String description;
    private boolean hasDescription;
} 