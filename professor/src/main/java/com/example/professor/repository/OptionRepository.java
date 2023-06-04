package com.example.professor.repository;

import com.example.professor.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, String> {

	List<Option> findAllByQuestionIdOrderByCreatedAt(String questionId);
	@Query(value = "select count(*) from option o where o.question_id = ?1", nativeQuery = true)
	Integer getOptionNumberByQuestionId(String questionId);

}
