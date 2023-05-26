package com.example.student.controller;

import com.example.student.model.ResetPasswordModel;
import com.example.student.service.StudentService;
import com.example.student.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class LoginController {

	final StudentService studentService;
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
		var student = studentService.getStudentByEmail(email);
		if (student != null && passwordEncoder.matches(password, student.getPassword())) {
			return "redirect:/my-skills/" + student.getId();
		} else {
			return "redirect:/login?error=true";
		}
	}

	@GetMapping("/logout")
	String logout() {
		return "redirect:/login";
	}


	@GetMapping("/reset-password/{resetCode}")
	public String getResetPasswordPage(@RequestParam(required = false) final String error, Model model, @PathVariable("resetCode") final String resetCode) {
		var student = studentService.getStudentByResetCode(resetCode);

		if (student == null) {
			return "redirect:/invalid-link";
		}

		model.addAttribute("resetCode", resetCode);
		String errorMessage = "";
		if (error != null) {
			errorMessage = switch (error) {
				case "err1" -> "New password and Confirm password must match.";
				case "err2" -> "Old password is incorrect.";
				case "err3" -> "New password cannot be Old password.";
				default -> "";
			};
		}
		model.addAttribute("error", errorMessage);

		return "reset-password";
	}

	@GetMapping("/invalid-link")
	public String getInvaidLinkPage() {
		return "invalid-link";
	}

	@PostMapping("/reset-password/submit/{resetCode}")
	public String resetPassword(@PathVariable("resetCode") final String resetCode, @ModelAttribute("passwords") ResetPasswordModel resetPasswordModel) {
		var student = studentService.getStudentByResetCode(resetCode);

		if (passwordEncoder.matches(resetPasswordModel.getOldPassword(), student.getPassword())) {
			if (resetPasswordModel.getNewPassword().equals(resetPasswordModel.getConfirmPassword())) {
				if (resetPasswordModel.getOldPassword().equals(resetPasswordModel.getNewPassword())) {
					return "redirect:/reset-password/" + resetCode + "?error=err3";
				} else {
					student.setPassword(passwordEncoder.encode(resetPasswordModel.getNewPassword()));
					student.setResetCode(Utils.getShortUUID());
					studentService.saveStudent(student);
					return "redirect:/confirm-reset";
				}
			}
			return "redirect:/reset-password/" + resetCode + "?error=err1";
		} else {
			return "redirect:/reset-password/" + resetCode + "?error=err2";
		}

	}

	@GetMapping("/confirm-reset")
	public String getConfirmResetPage() {
		return "confirm-reset";
	}

}
