package com.example.admin.repository;

import com.example.admin.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, String> {
	void deleteAllByStudentId(String studentId);
}
