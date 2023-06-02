package com.example.professor.service;

import com.example.professor.entity.Superuser;
import com.example.professor.model.SuperuserModel;
import com.example.professor.repository.SuperuserRepository;

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

	public void saveSuperUser(Superuser superuser) {
		superuserRepository.saveAndFlush(superuser);
	}

	public Superuser getSuperuserByEmail(String email) {
		return superuserRepository.findByEmail(email);
	}

	public void deleteSuperuser(String studentId) {
		superuserRepository.deleteById(studentId);
	}

	public Boolean isDuplicate(String name) {
		return superuserRepository.existsByName(name).isPresent();
	}

	public Boolean isDuplicateExcept(String name, String id) {
		return superuserRepository.existsByNameExcept(name, id).isPresent();
	}

	public Superuser getSuperuserByResetCode(String resetCode) {
		return superuserRepository.findByResetCode(resetCode);
	}

	public List<SuperuserModel> getStudentModelList() {
		var superusers = getAllSuperusers();
		var index = new AtomicInteger(1);
		return superusers.stream()
				.map(superuser -> new SuperuserModel(
						superuser.getId(),
						index.getAndIncrement(),
						superuser.getName(),
						superuser.getEmail(),
						superuser.getPhone(),
						superuser.getType()
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
