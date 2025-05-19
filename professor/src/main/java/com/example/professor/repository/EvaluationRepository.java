package com.example.professor.repository;

import com.example.professor.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
    @Query("""
    SELECT e
    FROM Evaluation e
    WHERE e.skillId = :skillId
    """)
    List<Evaluation> findBySkillId(String skillId);
} 