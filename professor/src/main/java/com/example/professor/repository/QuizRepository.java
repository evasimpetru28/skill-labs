package com.example.professor.repository;

import com.example.professor.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, String> {
	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isReady = false
			  and q.status <> "EXPIRED"
			""")
	List<Quiz> getAllBySuperuserIdNotReady(String superuserId);

	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.isReady = true
			  and q.status <> "EXPIRED"
			""")
	List<Quiz> getAllBySuperuserIdReady(String superuserId);
	@Query(value = """
			select q
			from Quiz q
			where q.superuserId = ?1
			  and q.status = "EXPIRED"
			""")
	List<Quiz> getAllExpiredBySuperuserId(String superuserId);
}
