package com.example.skilllabs.service;

import com.example.skilllabs.entity.Admin;
import com.example.skilllabs.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AdminService {

    final AdminRepository adminRepository;

    public void saveAdmin(Admin admin) {
        adminRepository.saveAndFlush(admin);
    }

    public Optional<Admin> getAdminByCredentials(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }

    public Admin getAdminById(String adminId) {
        return adminRepository.findById(adminId).get();
    }

}
