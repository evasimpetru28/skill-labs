package com.example.professor.repository;

import com.example.professor.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, String> {

	List<Response> findAllByQuestionId(String questionId);

}
