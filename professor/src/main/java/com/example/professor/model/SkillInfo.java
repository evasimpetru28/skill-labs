package com.example.professor.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SkillInfo implements Comparable<SkillInfo> {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private boolean hasDescription;

    @Override
    public int compareTo(SkillInfo other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
