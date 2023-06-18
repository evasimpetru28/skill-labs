package com.example.student.service;

import com.example.student.repository.OptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OptionService {

	final OptionRepository optionRepository;
}
