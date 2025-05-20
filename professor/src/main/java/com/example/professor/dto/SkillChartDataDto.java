package com.example.professor.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SkillChartDataDto {
    // Chart data
    private List<String> evaluationMonths;
    private List<Integer> evaluationCounts;
    private List<String> quizNames;
    private List<Double> quizScores;
} 