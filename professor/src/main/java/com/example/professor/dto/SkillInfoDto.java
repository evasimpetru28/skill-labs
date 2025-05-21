package com.example.professor.dto;

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
public class SkillInfoDto implements Comparable<SkillInfoDto> {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private boolean hasDescription;
    private boolean isBookmarked;

    @Override
    public int compareTo(SkillInfoDto other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
