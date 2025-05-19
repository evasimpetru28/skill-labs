package com.example.professor.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithSkills implements Comparable<CategoryWithSkills> {
    private CategoryInfo category;
    private List<SkillInfo> skills;

    @Override
    public int compareTo(CategoryWithSkills other) {
        return this.category.getName().compareToIgnoreCase(other.category.getName());
    }
}
