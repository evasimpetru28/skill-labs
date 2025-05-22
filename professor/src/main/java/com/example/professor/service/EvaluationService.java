package com.example.professor.service;

import com.example.professor.entity.Evaluation;
import com.example.professor.repository.EvaluationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationService {

    final EvaluationRepository evaluationRepository;

    public List<Evaluation> getEvaluationsBySkillId(String skillId) {
        return evaluationRepository.findBySkillId(skillId);
    }
} 