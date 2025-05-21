package com.example.professor.repository;

import com.example.professor.dto.QuizDto;
import com.example.professor.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isReady = false
			  and q.status <> "EXPIRED"
			order by q.createdAt desc
			""")
	List<Quiz> getAllBySuperuserIdNotReady(String superuserId);

	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isReady = true
			  and q.status <> "EXPIRED"
			order by q.createdAt desc
			""")
	List<Quiz> getAllBySuperuserIdReady(String superuserId);
	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.status = "EXPIRED"
			order by q.createdAt desc
			""")
	List<Quiz> getAllExpiredBySuperuserId(String superuserId);

	@Query(value = """
			select q
			from Quiz q
			where q.status = "PUBLIC"
			order by q.createdAt desc
			""")
	List<Quiz> getAllPublicQuizzes();

	List<Quiz> findBySkillId(String skillId);

	@Query("""
	SELECT new com.example.professor.dto.QuizDto(q.id, 0, q.superuserId, s.name, q.name, q.description,
	(CASE WHEN q.status = 'PUBLIC' THEN TRUE ELSE FALSE END), TO_CHAR(q.createdAt, 'DD.MM.YYYY (HH:mm)'), q.skillId)
	FROM Quiz q
	JOIN Superuser s ON q.superuserId = s.id
	WHERE q.skillId = :skillId
	AND q.isReady = true
	AND (q.status = 'PUBLIC' OR s.id = :superuserId)
	ORDER BY q.createdAt desc
	""")
	List<QuizDto> findQuizDtoBySkillId(String skillId, String superuserId);
}
