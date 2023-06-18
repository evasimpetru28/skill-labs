package com.example.admin.repository;

import com.example.admin.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, String> {
	List<Option> findAllByQuestionIdOrderByCreatedAt(String questionId);
}
