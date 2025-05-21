package com.example.student.repository;

import com.example.student.entity.Quiz;
import com.example.student.model.QuizInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, String> {
	@Query("""
	select a.id          as assignmentId,
		   q.id          as quizId,
		   a.createdAt   as createdAt,
		   q.name        as quizName,
		   q.description as description,
		   s2.id         as superuserId,
		   s2.name       as superuserName,
		   a.studentId   as studentId,
		   a.score       as score,
		   q.status 	 as status,
		   q.isExpired   as isExpired
	from Assignment a
			 join Quiz q on a.quizId = q.id
			 join Student s on a.studentId = s.id
			 join Superuser s2 on q.superuserId = s2.id
	where a.studentId = :studentId
	and q.isReady = true
	order by a.createdAt desc
	""")
	List<QuizInterface> findQuizzesAssignedByStudentId(@Param("studentId") String studentId);
}
