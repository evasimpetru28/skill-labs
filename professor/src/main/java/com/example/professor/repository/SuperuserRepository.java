package com.example.professor.repository;

import com.example.professor.entity.Superuser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperuserRepository extends JpaRepository<Superuser, String> {
	Superuser findByResetCode(String resetCode);
	Superuser findByEmail(String email);
}
