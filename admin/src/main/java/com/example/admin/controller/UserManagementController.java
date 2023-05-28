package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.Page;
import com.example.admin.entity.Student;
import com.example.admin.model.ResetPasswordModel;
import com.example.admin.service.*;
import com.example.admin.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
//@RequestMapping("/admin")
public class UserManagementController {

	final AdminService adminService;
	final NavbarService navbarService;
	final SmtpMailSender smtpMailSender;
	final StudentService studentService;
	final ResponseService responseService;
	final AssignmentService assignmentService;
	final EvaluationService evaluationService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/users")
	String getUserManagementPage() {
		return "redirect:/users/admins";
	}

	@GetMapping("/users/students")
	String getStudentsPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		model.addAttribute("userList", studentService.getStudentModelList());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("admins", false);
		model.addAttribute("students", true);
		model.addAttribute("superusers", false);
		return "user-management";
	}

	@GetMapping("/users/admins")
	String getAdminsPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		model.addAttribute("userList", adminService.getAdminModelList());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("admins", true);
		model.addAttribute("students", false);
		model.addAttribute("superusers", false);
		return "user-management";
	}

	@GetMapping("/users/professors-companies")
	String getSuperusersPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("admins", false);
		model.addAttribute("students", false);
		model.addAttribute("superusers", true);
		return "user-management";
	}

	@PostMapping("/add-student")
	public String addStudent(@ModelAttribute("student") Student student) {
		var educationInfo = student.getProgram().split(",");
		student.setProgram(educationInfo[0]);
		student.setDomain(educationInfo[1]);
		student.setYear(Integer.parseInt(educationInfo[2]));

		if (studentService.isDuplicate(student.getEmail())) {
			return "redirect:/users?duplicate=true";
		} else {
			var password = Utils.getShortUUID();
			var resetCode = Utils.getShortUUID();
			student.setResetCode(resetCode);
			student.setPassword(passwordEncoder.encode(password));

			studentService.saveStudent(student);
			smtpMailSender.sendMailResetPassword(true, student.getName(), student.getEmail(), password, resetCode);
		}
		return "redirect:/users";
	}

	@PostMapping("/add-admin")
	public String addAdmin(@ModelAttribute("admin") Admin admin) {
		if (adminService.isDuplicate(admin.getEmail())) {
			return "redirect:/users/admins?duplicate=true";
		} else {
			var password = Utils.getShortUUID();
			var resetCode = Utils.getShortUUID();
			admin.setResetCode(resetCode);
			admin.setPassword(passwordEncoder.encode(password));

			adminService.saveAdmin(admin);
			smtpMailSender.sendMailResetPassword(false, admin.getName(), admin.getEmail(), password, resetCode);
		}
		return "redirect:/users/admins";
	}

	@PostMapping("/delete-student/{studentId}")
	public String deleteStudent(@PathVariable String studentId) {
		evaluationService.deleteAllEvaluationsForStudent(studentId);
		assignmentService.deleteAllAssignmentsForStudent(studentId);
		responseService.deleteAllResponsesForStudent(studentId);
		studentService.deleteStudent(studentId);
		return "redirect:/users";
	}

	@PostMapping("/delete-admin/{adminId}")
	public String deleteAdmin(@PathVariable String adminId) {
		adminService.deleteAdmin(adminId);
		return "redirect:/users/admins";
	}

	@PostMapping("/edit-student/{studentId}")
	public String editStudent(@ModelAttribute("student") Student student, @PathVariable("studentId") final String id) {
		var educationInfo = student.getProgram().split(",");
		student.setProgram(educationInfo[0]);
		student.setDomain(educationInfo[1]);
		student.setYear(Integer.parseInt(educationInfo[2]));
		student.setId(id);
		if (studentService.isDuplicateExcept(student.getEmail(), student.getId())) {
			return "redirect:/users?duplicate=true";
		} else {
			var oldStudent = studentService.getStudentById(id);
			student.setPassword(oldStudent.getPassword());
			student.setResetCode(oldStudent.getResetCode());
			studentService.saveStudent(student);
		}
		return "redirect:/users";
	}

	@PostMapping("/edit-admin/{adminId}")
	public String editAdmin(@ModelAttribute("admin") Admin admin, @PathVariable("adminId") final String id) {
		admin.setId(id);
		if (adminService.isDuplicateExcept(admin.getEmail(), admin.getId())) {
			return "redirect:/users/admins?duplicate=true";
		} else {
			var oldAdmin = adminService.getAdminById(id);
			admin.setPassword(oldAdmin.getPassword());
			admin.setResetCode(oldAdmin.getResetCode());
			adminService.saveAdmin(admin);
		}
		return "redirect:/users/admins";
	}

	@GetMapping("/reset-password/{resetCode}")
	public String getResetPasswordPage(@RequestParam(required = false) final String error, Model model, @PathVariable("resetCode") final String resetCode) {
		var admin = adminService.getAdminByResetCode(resetCode);

		if (admin == null) {
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
		var admin = adminService.getAdminByResetCode(resetCode);

		if (passwordEncoder.matches(resetPasswordModel.getOldPassword(), admin.getPassword())) {
			if (resetPasswordModel.getNewPassword().equals(resetPasswordModel.getConfirmPassword())) {
				if (resetPasswordModel.getOldPassword().equals(resetPasswordModel.getNewPassword())) {
					return "redirect:/reset-password/" + resetCode + "?error=err3";
				} else {
					admin.setPassword(passwordEncoder.encode(resetPasswordModel.getNewPassword()));
					admin.setResetCode(Utils.getShortUUID());
					adminService.saveAdmin(admin);
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
