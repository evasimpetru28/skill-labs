package com.example.admin.repository;

import com.example.admin.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {

	void deleteAllByStudentId(String studentId);
	void deleteAllBySkillId(String skillId);
}
