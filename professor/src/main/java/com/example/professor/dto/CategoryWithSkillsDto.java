package com.example.professor.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithSkillsDto implements Comparable<CategoryWithSkillsDto> {
    private CategoryInfoDto category;
    private List<SkillInfoDto> skills;

    @Override
    public int compareTo(CategoryWithSkillsDto other) {
        return this.category.getName().compareToIgnoreCase(other.category.getName());
    }
}
