package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.service.AdminService;
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

	final AdminService adminService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String getRegisterPage(@RequestParam(required = false) final boolean error, Model model) {
		model.addAttribute("error", error);
		return "register";
	}

	@PostMapping("/register/submit")
	public String submitRegister(@ModelAttribute("admin") Admin admin,
								 @ModelAttribute("confirmPassword") String confirmPassword) {

		if ("".equals(admin.getPhone())) {
			admin.setPhone(null);
		}

		if (!confirmPassword.equals(admin.getPassword())) {
			return "redirect:/register?error=true";
		} else {

			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			adminService.saveAdmin(admin);
			return "redirect:/login";
		}

	}

}
