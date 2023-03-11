package com.example.skilllabs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillModel {

    private String id;
    private Integer index;
    private String name;
    private String description;
    private String category;
    private Boolean hasDescription;
}
