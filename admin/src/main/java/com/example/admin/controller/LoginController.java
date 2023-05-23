package com.example.admin.controller;

import com.example.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class LoginController {

	final AdminService adminService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	String getStartPage() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	String getLoginPage(@RequestParam(required = false) final boolean error, Model model) {
		model.addAttribute("error", error);
		return "login";
	}

	@PostMapping("/login/submit")
	String submitLogin(@RequestParam String email, @RequestParam String password) {
		var admin = adminService.getAdminByEmail(email);
		if (admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword())) {
			return "redirect:/dashboard";
		} else {
			return "redirect:/login?error=true";
		}
	}

	@GetMapping("/logout")
	String logout() {
		return "redirect:/login";
	}

}
