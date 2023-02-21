package com.example.skilllabs.repository;

import com.example.skilllabs.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository  extends JpaRepository<Admin,String> {

    Optional<Admin> findByEmailAndPassword(String email, String password);

}
