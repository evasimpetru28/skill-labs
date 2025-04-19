package com.example.admin.repository;

import com.example.admin.entity.Superuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SuperuserRepository extends JpaRepository<Superuser, String> {
	Superuser findByResetCode(String resetCode);
	
	List<Superuser> findAllByOrderByName();
	
	Superuser findByEmail(String email);
	
	@Query("select s from Superuser s where lower(s.email) = lower(?1)")
	Optional<Superuser> existsByEmail(String email);
		
	@Query("select s from Superuser s where lower(s.email) = lower(?1) and s.id <> ?2")
	Optional<Superuser> existsByEmailExcept(String email, String id);
	
	List<Superuser> findAllByTypeOrderByName(String type);
}
