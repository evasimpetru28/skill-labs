package com.example.professor.repository;

import com.example.professor.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, String> {

	List<Option> findAllByQuestionIdOrderByCreatedAt(String questionId);
	@Query("select count(*) from Option o where o.questionId = ?1")
	Integer getOptionNumberByQuestionId(String questionId);

}
