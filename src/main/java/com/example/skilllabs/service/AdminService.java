package com.example.skilllabs.service;

import com.example.skilllabs.entity.Admin;
import com.example.skilllabs.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AdminService {

    final AdminRepository adminRepository;

    public void saveAdmin(Admin admin) {
        adminRepository.saveAndFlush(admin);
    }
}
