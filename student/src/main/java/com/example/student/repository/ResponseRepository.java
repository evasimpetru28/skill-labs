package com.example.student.repository;

import com.example.student.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, String> {

	@Query("select r.id from Response r where r.studentId = ?1 and r.optionId = ?2")
	Optional<String> findByStudentIdAndOptionId(String studentId, String optionId);
	@Query(value = "select r.question_id from response r where r.student_id = ?1\n and r.question_id in ?2 group by r.question_id ", nativeQuery = true)
	List<String> getAnsweredQuestionsByStudentIdAndQuestionIdInList(String studentId, List<String> questionIdList);
	@Query(value = """
		select r.optionId
		from Response r
		join Option o on o.id = r.optionId
		where r.studentId = ?1
		and r.questionId = ?2
		order by o.createdAt 
		""")
	List<String> getSelectedOptionsByStudentIdAndQuestionId(String studentId, String questionId);
}
