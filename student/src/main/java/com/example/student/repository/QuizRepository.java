package com.example.student.repository;

import com.example.student.entity.Quiz;
import com.example.student.model.QuizInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, String> {
	@Query(value = """
			select a.id          as assignmentId,
			       q.id          as quizId,
			       a.created_at  as createdAt,
			       q.name        as quizName,
			       q.description as description,
			       s2.id         as superuserId,
			       s2.name       as superuserName,
			       a.student_id  as studentId,
			       a.score       as score,
			       q.status 	 as status
			from assignment a
			         join quiz q on a.quiz_id = q.id
			         join student s on a.student_id = s.id
			         join superuser s2 on q.superuser_id = s2.id
			where a.student_id = :studentId
			order by a.created_at desc
			""", nativeQuery = true)
	List<QuizInterface> findQuizzesAssignedByStudentId(@Param("studentId") String studentId);
}
