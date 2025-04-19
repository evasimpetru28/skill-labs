package com.example.admin.repository;

import com.example.admin.entity.Evaluation;
import com.example.admin.model.PopularSkillModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {

	void deleteAllByStudentId(String studentId);
	void deleteAllBySkillId(String skillId);

	@Query(value = """
			SELECT new com.example.admin.model.PopularSkillModel(s.name, COUNT(e.id))
			FROM Evaluation e
			JOIN Skill s on s.id = e.skillId
			GROUP BY s.name
			ORDER BY COUNT(e.id) DESC
			LIMIT ?1
			""")
	List<PopularSkillModel> findPopularSkills(int limit);

	@Query("SELECT COUNT(DISTINCT e.studentId) FROM Evaluation e")
	Integer countStudentsWithEvaluations();

	@Query("SELECT CAST(COUNT(e.id) AS double) / COUNT(DISTINCT e.studentId) FROM Evaluation e")
	Double getAverageEvaluationsPerStudent();
}
