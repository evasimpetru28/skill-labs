package com.example.professor.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SkillChartDataDTO {
    // Chart data
    private List<String> evaluationMonths;
    private List<Integer> evaluationCounts;
    private List<String> quizNames;
    private List<Double> quizScores;
} 