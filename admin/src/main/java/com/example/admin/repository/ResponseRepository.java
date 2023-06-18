package com.example.admin.repository;

import com.example.admin.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, String> {
	void deleteAllByStudentId(String studentId);
	List<Response> findAllByQuestionId(String questionId);
}
