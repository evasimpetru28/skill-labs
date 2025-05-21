package com.example.professor.service;

import com.example.professor.entity.Superuser;
import com.example.professor.repository.SuperuserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Superuser getSuperuserById(String id) {
		return superuserRepository.getReferenceById(id);
	}

	public Superuser getSuperuserByResetCode(String resetCode) {
		return superuserRepository.findByResetCode(resetCode);
	}

}
