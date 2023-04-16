package com.example.skilllabs.service;

import com.example.skilllabs.entity.Admin;
import com.example.skilllabs.entity.Student;
import com.example.skilllabs.model.AdminModel;
import com.example.skilllabs.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class AdminService {

	final AdminRepository adminRepository;

	public void saveAdmin(Admin admin) {
		adminRepository.saveAndFlush(admin);
	}

	public void deleteAdmin(String adminId) {
		adminRepository.deleteById(adminId);
	}

	public Boolean isDuplicate(String name) {
		return adminRepository.existsByName(name).isPresent();
	}

	public Boolean isDuplicateExcept(String name, String id) {
		return adminRepository.existsByNameExcept(name, id).isPresent();
	}

	public Optional<Admin> getAdminByCredentials(String email, String password) {
		return adminRepository.findByEmailAndPassword(email, password);
	}

	public Admin getAdminById(String adminId) {
		return adminRepository.getReferenceById(adminId);
	}

	public List<AdminModel> getAdminModelList() {
		var admins = getAllAdmins();
		var index = new AtomicInteger(1);
		return admins.stream()
				.map(admin -> new AdminModel(
						index.getAndIncrement(),
						admin.getId(),
						admin.getName(),
						admin.getEmail(),
						admin.getPhone(),
						admin.getPassword()
				))
				.toList();
	}

	public List<Admin> getAllAdmins() {
		return adminRepository.findAllByOrderByName();
	}

}
