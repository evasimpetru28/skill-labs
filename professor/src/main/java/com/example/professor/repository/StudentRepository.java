package com.example.professor.repository;

import com.example.professor.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
	@Query("select concat(concat('\"', s.name), '\"') from Student s where s.id not in (select a.studentId from Assignment a where a.quizId = ?1) order by lower(s.name)")
	List<String> findAllNotAssignedByQuizOrderByName(String quizId);
	Student findStudentByName(String name);

	@Query("""
	SELECT s
	FROM Student s
	JOIN Assignment a ON a.studentId = s.id
	JOIN  Quiz q ON q.id = a.quizId
	WHERE q.skillId = :skillId
	""")
	List<Student> findStudentsByAnsweredQuizSkillId(String skillId);
}
