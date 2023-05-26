package com.example.student.repository;

import com.example.student.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
	Optional<Evaluation> findBySkillIdAndStudentId(String skillId, String studentId);
}
