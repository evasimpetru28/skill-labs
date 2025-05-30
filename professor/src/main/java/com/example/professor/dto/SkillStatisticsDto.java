package com.example.professor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillStatisticsDto {
    private int totalStudents;
    private int evaluationCount;
    private int quizCount;
    private double averageScore;
    
    private int beginnerCount;
    private int intermediateCount;
    private int advancedCount;
    private double beginnerPercentage;
    private double intermediatePercentage;
    private double advancedPercentage;
} 