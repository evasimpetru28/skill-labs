package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class RegisterController {

	final StudentService studentService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String getRegisterPage(@RequestParam(required = false) final boolean error, Model model) {
		model.addAttribute("error", error);
		return "register";
	}

	@PostMapping("/register/submit")
	public String submitRegister(@ModelAttribute("admin") Student student,
								 @ModelAttribute("confirmPassword") String confirmPassword) {

		if ("".equals(student.getPhone())) {
			student.setPhone(null);
		}

		if (!confirmPassword.equals(student.getPassword())) {
			return "redirect:/register?error=true";
		} else {

			student.setPassword(passwordEncoder.encode(student.getPassword()));
			studentService.saveStudent(student);
			return "redirect:/login";
		}

	}

}
