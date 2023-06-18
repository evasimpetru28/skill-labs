package com.example.student.repository;

import com.example.student.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, String> {

	@Query("select r.id from Response r where r.studentId = ?1 and r.optionId = ?2")
	Optional<String> findByStudentIdAndOptionId(String studentId, String optionId);
}
