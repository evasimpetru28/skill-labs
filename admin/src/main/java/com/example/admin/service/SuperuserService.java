package com.example.admin.service;

import com.example.admin.entity.Superuser;
import com.example.admin.model.SuperuserModel;
import com.example.admin.repository.SuperuserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class SuperuserService {

	final SuperuserRepository superuserRepository;

	public void saveSuperuser(Superuser superuser) {
		superuserRepository.saveAndFlush(superuser);
	}

	public Superuser getSuperuserByEmail(String email) {
		return superuserRepository.findByEmail(email);
	}

	public void deleteSuperuser(String studentId) {
		superuserRepository.deleteById(studentId);
	}

	public Boolean isDuplicate(String email) {
		return superuserRepository.existsByEmail(email).isPresent();
	}

	public Boolean isDuplicateExcept(String email, String id) {
		return superuserRepository.existsByEmailExcept(email, id).isPresent();
	}

	public Superuser getSuperuserByResetCode(String resetCode) {
		return superuserRepository.findByResetCode(resetCode);
	}

	public List<SuperuserModel> getSuperuserModelList() {
		return convertToModelList(getAllSuperusers());
	}

	public List<SuperuserModel> getSuperuserModelListByType(String type) {
		return convertToModelList(superuserRepository.findAllByTypeOrderByName(type));
	}

	private List<SuperuserModel> convertToModelList(List<Superuser> superusers) {
		var index = new AtomicInteger(1);
		return superusers.stream()
				.map(superuser -> new SuperuserModel(
						superuser.getId(),
						index.getAndIncrement(),
						superuser.getName(),
						superuser.getEmail(),
						superuser.getPhone(),
						superuser.getType(),
						"PROFESSOR".equals(superuser.getType())
				))
				.toList();
	}

	public List<Superuser> getAllSuperusers() {
		return superuserRepository.findAllByOrderByName();
	}

	public Superuser getSuperuserById(String id) {
		return superuserRepository.getReferenceById(id);
	}

}
