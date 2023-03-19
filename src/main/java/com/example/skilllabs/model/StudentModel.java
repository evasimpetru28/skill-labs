package com.example.skilllabs.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class StudentModel {
    String id;
    Integer index;
    String name;
    String email;
    String password;
    String phone;
    Integer year;
    String program;
    String domain;
}
