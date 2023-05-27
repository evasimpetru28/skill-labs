package com.example.student.repository;

import com.example.student.entity.Evaluation;
import com.example.student.model.SkillEvaluationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
	Optional<Evaluation> findBySkillIdAndStudentId(String skillId, String studentId);
	@Query(value = """
			select new com.example.student.model.SkillEvaluationModel(s.id, s.name, s.description, c.name, true, e.interest, e.knowledge, e.experience)
			from Skill s
			join Category c on s.categoryId = c.id
			join Evaluation e on s.id = e.skillId
			where e.studentId = :studentId
					""")
	List<SkillEvaluationModel> findAllEvaluationsByStudentId(@Param("studentId") String studentId);
}
