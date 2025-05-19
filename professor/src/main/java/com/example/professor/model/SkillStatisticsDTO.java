package com.example.professor.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SkillStatisticsDTO {
    private int totalStudents;
    private int evaluationCount;
    private int quizCount;
    private double averageScore;
    
    // Skill mastery distribution
    private int beginnerCount;
    private int intermediateCount;
    private int advancedCount;
    private double beginnerPercentage;
    private double intermediatePercentage;
    private double advancedPercentage;
    
    // Chart data
    private List<String> evaluationMonths;
    private List<Integer> evaluationCounts;
    private List<String> quizNames;
    private List<Double> quizScores;
} 