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
	Optional<Admin> findByEmail(String email);

	List<Admin> findAllByOrderByName();

	@Query("select a from Admin a where lower(a.name) = lower(?1)")
	Optional<Admin> existsByName(String name);

	@Query("select a from Admin a where lower(a.name) = lower(?1) and a.id <> ?2")
	Optional<Admin> existsByNameExcept(String name, String id);

}
