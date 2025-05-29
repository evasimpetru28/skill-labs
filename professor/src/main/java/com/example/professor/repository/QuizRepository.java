package com.example.professor.repository;

import com.example.professor.dto.QuizCompletionInfoDto;
import com.example.professor.dto.QuizDto;
import com.example.professor.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isReady = false
			  and q.isExpired = false
			order by q.createdAt desc
			""")
	List<Quiz> getAllBySuperuserIdNotReady(String superuserId);

	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isReady = true
			  and q.isExpired = false
			order by q.createdAt desc
			""")
	List<Quiz> getAllBySuperuserIdReady(String superuserId);
	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isExpired = true
			order by q.createdAt desc
			""")
	List<Quiz> getAllExpiredBySuperuserId(String superuserId);

	@Query(value = """
			select q
			from Quiz q
			where q.status = "PUBLIC"
			and q.isReady = true
			order by q.createdAt desc
			""")
	List<Quiz> getAllPublicQuizzes();

	List<Quiz> findBySkillId(String skillId);

	@Query(value = """
	SELECT new com.example.professor.dto.QuizCompletionInfoDto(CAST(ROUND(COUNT(CASE WHEN A.score IS NOT NULL THEN A.id END) * 1.0 / COUNT(A.id) * 100, 2) AS java.math.BigDecimal),
	       COUNT(CASE WHEN A.score IS NOT NULL THEN A.id END),
	       COUNT(A.id))
	FROM Quiz Q
	JOIN Assignment A ON A.quizId = Q.id
	WHERE Q.superuserId = :superuserId
	""")
	QuizCompletionInfoDto getPercentageOfCompletionQuizzesBySuperuserId(String superuserId);

	@Query("""
	SELECT new com.example.professor.dto.QuizDto(q.id, 0, q.superuserId, s.name, q.name, q.description,
	(CASE WHEN q.status = 'PUBLIC' THEN TRUE ELSE FALSE END), TO_CHAR(q.createdAt, 'DD.MM.YYYY (HH:mm)'), q.skillId, q.isExpired)
	FROM Quiz q
	JOIN Superuser s ON q.superuserId = s.id
	WHERE q.skillId = :skillId
	AND q.isReady = true
	AND (q.status = 'PUBLIC' OR s.id = :superuserId)
	ORDER BY q.createdAt desc
	""")
	List<QuizDto> findQuizDtoBySkillId(String skillId, String superuserId);

	@Query("""
	SELECT new com.example.professor.dto.QuizDto(q.id, 0, q.superuserId, s.name, q.name, q.description,
	(CASE WHEN q.status = 'PUBLIC' THEN TRUE ELSE FALSE END), TO_CHAR(q.createdAt, 'DD.MM.YYYY (HH:mm)'),
	s.name || ' (' || c.name || ')', q.isExpired)
	FROM Quiz q
	JOIN Superuser s ON q.superuserId = s.id
	JOIN Skill sk ON q.skillId = sk.id
	JOIN Category c ON c.id = sk.categoryId
	WHERE q.id = :id
	""")
	QuizDto getQuizDtoById(String id);

	long countBySuperuserId(String superuserId);

	@Query("""
	SELECT DISTINCT q
	FROM Quiz q
	JOIN Assignment a ON q.id = a.quizId
	WHERE q.superuserId = :superuserId
	""")
	List<Quiz> findBySuperuserIdAndHasAssignments(String superuserId);

	@Query(value = """
	SELECT Q.NAME
	FROM quiz Q
	JOIN assignment A ON A.quiz_id = Q.id
	WHERE A.score IS NOT NULL
	AND Q.superuser_id = :superuserId
	GROUP BY Q.ID, Q.name
	ORDER BY COUNT(A.ID) DESC
	LIMIT 1
	""", nativeQuery = true)
	String getQuizNameWithMaxAssignmentsBySuperuserId(String superuserId);

	@Query(value = """
	SELECT COUNT(S.STUDENT_ID)
	FROM (SELECT DISTINCT A.student_id
		FROM assignment A
		JOIN QUIZ Q ON Q.ID = A.quiz_id
		JOIN EVALUATION E ON E.skill_id = Q.skill_id AND E.student_id = A.student_id
		WHERE A.score IS NOT NULL
		AND Q.superuser_id = :superuserId) S
	""", nativeQuery = true)
	long countUniqueStudentsQuizSubmissionsWithEvaluatedSkill(String superuserId);

}
