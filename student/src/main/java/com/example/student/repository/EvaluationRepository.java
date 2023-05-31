package com.example.student.repository;

import com.example.student.entity.Evaluation;
import com.example.student.model.SkillEvaluationInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
	Optional<Evaluation> findBySkillIdAndStudentId(String skillId, String studentId);
	@Query(value = """
			select s.id as id,
			 s.name as name, 
			 s.description as description,
			 c.name as category,
			 true as hasEvaluation, 
			 case 
			 	when s.description = '' then false 
			 	else true
			 end as hasDescription,
			 e.interest as interest,
			 e.knowledge as knowledge, 
			 e.experience as experience
			from skill s
			join category c on s.category_id = c.id
			join evaluation e on s.id = e.skill_id
			where e.student_id = :studentId
			order by lower(c.name), lower(s.name)
					""", nativeQuery = true)
	List<SkillEvaluationInterface> findAllEvaluationsByStudentId(@Param("studentId") String studentId);

}
