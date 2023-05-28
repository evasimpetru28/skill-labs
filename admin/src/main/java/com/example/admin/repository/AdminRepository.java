package com.example.admin.repository;

import com.example.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	Admin findByResetCode(String resetCode);
	Admin findByEmail(String email);
	List<Admin> findAllByOrderByName();
	@Query("select a from Admin a where lower(a.email) = lower(?1)")
	Optional<Admin> existsByEmail(String email);
	@Query("select a from Admin a where lower(a.email) = lower(?1) and a.id <> ?2")
	Optional<Admin> existsByEmailExcept(String email, String id);
}
