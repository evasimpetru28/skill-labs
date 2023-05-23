package com.example.admin.service;

import com.example.admin.entity.Admin;
import com.example.admin.model.AdminModel;
import com.example.admin.repository.AdminRepository;
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

	public Optional<Admin> getAdminByEmail(String email) {
		return adminRepository.findByEmail(email);
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
						admin.getPhone()
				))
				.toList();
	}

	public List<Admin> getAllAdmins() {
		return adminRepository.findAllByOrderByName();
	}

	public Optional<Admin> getOptionalAdminById(String id) {
		return adminRepository.findById(id);
	}

}
