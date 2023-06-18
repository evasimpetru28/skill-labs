package com.example.admin.repository;

import com.example.admin.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, String> {
	List<Quiz> findAllBySuperuserId(String superuserId);
}
