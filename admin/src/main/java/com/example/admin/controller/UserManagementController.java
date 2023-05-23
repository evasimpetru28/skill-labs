package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.Page;
import com.example.admin.entity.Student;
import com.example.admin.service.AdminService;
import com.example.admin.service.NavbarService;
import com.example.admin.service.SmtpMailSender;
import com.example.admin.service.StudentService;
import com.example.admin.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserManagementController {

	final NavbarService navbarService;
	final StudentService studentService;
	final AdminService adminService;
	final SmtpMailSender smtpMailSender;

	@GetMapping("/users")
	String getUserManagementPage() {
		return "redirect:/users/students";
	}

	@GetMapping("/users/students")
	String getStudentsPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		model.addAttribute("userList", studentService.getStudentModelList());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("adminUsers", false);
		return "user-management";
	}

	@GetMapping("/users/admins")
	String getAdminsPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		model.addAttribute("userList", adminService.getAdminModelList());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("adminUsers", true);
		return "user-management";
	}

	@PostMapping("/add-student")
	public String addStudent(@ModelAttribute("student") Student student) {
		var educationInfo = student.getProgram().split(",");
		student.setProgram(educationInfo[0]);
		student.setDomain(educationInfo[1]);
		student.setYear(Integer.parseInt(educationInfo[2]));

		if (studentService.isDuplicate(student.getName())) {
			return "redirect:/users?duplicate=true";
		} else {
			var password = Utils.getShortUUID();
			var resetCode = Utils.getShortUUID();
			student.setPassword(password);
			student.setResetCode(resetCode);

			studentService.saveStudent(student);
			smtpMailSender.sendMailResetPassword(student.getEmail(), resetCode);
		}
		return "redirect:/users";
	}
	private String getRandomLink() {
		//save to db random generated code for reset psswd link
		return "link!";
	}

	@PostMapping("/add-admin")
	public String addAdmin(@ModelAttribute("admin") Admin admin) {
		if (adminService.isDuplicate(admin.getName())) {
			return "redirect:/users/admins?duplicate=true";
		} else {
			adminService.saveAdmin(admin);
		}
		return "redirect:/users/admins";
	}

	@PostMapping("/delete-student/{studentId}")
	public String deleteStudent(@PathVariable String studentId) {
		// Delete all evalations, quizzez
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
		if (studentService.isDuplicateExcept(student.getName(), student.getId())) {
			return "redirect:/users?duplicate=true";
		} else {
			studentService.saveStudent(student);
		}
		return "redirect:/users";
	}

	@PostMapping("/edit-admin/{adminId}")
	public String editAdmin(@ModelAttribute("admin") Admin admin, @PathVariable("adminId") final String id) {
		admin.setId(id);
		if (adminService.isDuplicateExcept(admin.getName(), admin.getId())) {
			return "redirect:/users/admins?duplicate=true";
		} else {
			adminService.saveAdmin(admin);
		}
		return "redirect:/users/admins";
	}
}
