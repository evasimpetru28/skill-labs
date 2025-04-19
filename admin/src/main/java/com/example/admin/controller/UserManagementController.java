package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.Page;
import com.example.admin.entity.Student;
import com.example.admin.entity.Superuser;
import com.example.admin.model.ResetPasswordModel;
import com.example.admin.model.StudentModel;
import com.example.admin.model.SuperuserModel;
import com.example.admin.service.*;
import com.example.admin.util.Utils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
//@RequestMapping("/admin")
public class UserManagementController {

	final QuizService quizService;
	final AdminService adminService;
	final NavbarService navbarService;
	final OptionService optionService;
	final SmtpMailSender smtpMailSender;
	final StudentService studentService;
	final ResponseService responseService;
	final QuestionService questionService;
	final SuperuserService superuserService;
	final AssignmentService assignmentService;
	final EvaluationService evaluationService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Value("${admin.name}")
	String adminName;
	@Value("${admin.mail}")
	String adminMail;
	@Value("${admin.password}")
	String adminPassword;

	@PostConstruct
	public void defaultAdmin() {
		if (!adminService.isDuplicate(adminMail)) {
			var admin = new Admin();
			admin.setName(adminName);
			admin.setEmail(adminMail);
			admin.setPhone("0000000000");
			admin.setResetCode(Utils.getShortUUID());
			admin.setPassword(passwordEncoder.encode(adminPassword));

			adminService.saveAdmin(admin);
		}
	}

	@GetMapping("/users")
	String getUserManagementPage() {
		return "redirect:/users/admins";
	}

	@GetMapping("/users/students")
	String getStudentsPage(Model model, 
			@RequestParam(required = false) final Boolean duplicate,
			@RequestParam(required = false) final String program,
			@RequestParam(required = false) final String domain,
			@RequestParam(required = false) final Integer year) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		
		List<StudentModel> students;
		boolean hasFilters = false;
		
		if (program != null && !program.isEmpty()) {
			students = studentService.getStudentModelListByProgram(program);
			model.addAttribute("isLicenta", "Licenta".equals(program));
			model.addAttribute("isMaster", "Master".equals(program));
			model.addAttribute("isDoctorat", "Doctorat".equals(program));
			hasFilters = true;
		} else if (domain != null && !domain.isEmpty()) {
			students = studentService.getStudentModelListByDomain(domain);
			model.addAttribute("isMatematica", "Matematica".equals(domain));
			model.addAttribute("isInformaticaIF", "Informatica IF".equals(domain));
			model.addAttribute("isInformaticaID", "Informatica ID".equals(domain));
			model.addAttribute("isCTI", "CTI".equals(domain));
			hasFilters = true;
		} else if (year != null) {
			students = studentService.getStudentModelListByYear(year);
			model.addAttribute("isYear1", year == 1);
			model.addAttribute("isYear2", year == 2);
			model.addAttribute("isYear3", year == 3);
			model.addAttribute("isYear4", year == 4);
			hasFilters = true;
		} else {
			students = studentService.getStudentModelList();
		}
		
		model.addAttribute("userList", students);
		model.addAttribute("hasUsers", !students.isEmpty());
		model.addAttribute("hasFilters", hasFilters);
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("admins", false);
		model.addAttribute("students", true);
		model.addAttribute("superusers", false);
		return "user-management";
	}

	@GetMapping("/users/admins")
	String getAdminsPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		var admins = adminService.getAdminModelList();
		model.addAttribute("userList", admins);
		model.addAttribute("hasUsers", !admins.isEmpty());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("admins", true);
		model.addAttribute("students", false);
		model.addAttribute("superusers", false);
		return "user-management";
	}

	@GetMapping("/users/professors-companies")
	String getSuperusersPage(Model model, 
			@RequestParam(required = false) final Boolean duplicate,
			@RequestParam(required = false) final String type) {
		navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
		
		List<SuperuserModel> superusers;
		boolean hasFilters = false;
		
		if (type != null && !type.isEmpty()) {
			superusers = superuserService.getSuperuserModelListByType(type);
			model.addAttribute("isProfessor", "PROFESSOR".equals(type));
			model.addAttribute("isCompany", "COMPANY".equals(type));
			model.addAttribute("selectedType", "PROFESSOR".equals(type) ? "professors" : "companies");
			hasFilters = true;
		} else {
			superusers = superuserService.getSuperuserModelList();
		}
		
		model.addAttribute("userList", superusers);
		model.addAttribute("hasUsers", !superusers.isEmpty());
		model.addAttribute("hasFilters", hasFilters);
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
			return "redirect:/users/students?duplicate=true";
		} else {
			var password = Utils.getShortUUID();
			var resetCode = Utils.getShortUUID();
			student.setResetCode(resetCode);
			student.setPassword(passwordEncoder.encode(password));

			studentService.saveStudent(student);
			smtpMailSender.sendMailResetPassword("STUDENT", student.getName(), student.getEmail(), password, resetCode);
		}
		return "redirect:/users/students";
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
			smtpMailSender.sendMailResetPassword("ADMIN", admin.getName(), admin.getEmail(), password, resetCode);
		}
		return "redirect:/users/admins";
	}

	@PostMapping("/add-superuser")
	public String addSuperuser(@ModelAttribute("superuser") Superuser superuser) {
		if (superuserService.isDuplicate(superuser.getEmail())) {
			return "redirect:/users/professors-companies?duplicate=true";
		} else {
			var password = Utils.getShortUUID();
			var resetCode = Utils.getShortUUID();
			superuser.setResetCode(resetCode);
			superuser.setPassword(passwordEncoder.encode(password));

			superuserService.saveSuperuser(superuser);
			smtpMailSender.sendMailResetPassword("SUPERUSER", superuser.getName(), superuser.getEmail(), password, resetCode);
		}
		return "redirect:/users/professors-companies";
	}

	@PostMapping("/delete-student/{studentId}")
	public String deleteStudent(@PathVariable String studentId) {
		evaluationService.deleteAllEvaluationsForStudent(studentId);
		assignmentService.deleteAllAssignmentsForStudent(studentId);
		responseService.deleteAllResponsesForStudent(studentId);
		studentService.deleteStudent(studentId);
		return "redirect:/users/students";
	}

	@PostMapping("/delete-admin/{adminId}")
	public String deleteAdmin(@PathVariable String adminId) {
		adminService.deleteAdmin(adminId);
		return "redirect:/users/admins";
	}

	@PostMapping("/delete-superuser/{superuserId}")
	public String deleteSuperuser(@PathVariable String superuserId) {
		var quizzes = quizService.getAllBySuperuser(superuserId);
		quizzes.forEach(quiz -> {
			var questions = questionService.getAllQuestionsByQuizId(quiz.getId());
			questions.forEach(question -> {
				log.info("Delete all responses for question with id {}", question.getId());
				responseService.deleteAllResponsesByQuestionId(question.getId());
				log.info("Delete all options for question with id {}", question.getId());
				optionService.deleteOptionsOfQuestion(question.getId());
				log.info("Delete question with id {}", question.getId());
				questionService.deleteQuestionById(question.getId());
			});

			log.info("Delete assignments for quiz with id {}", quiz.getId());
			assignmentService.deleteAllAssignmentsForQuizId(quiz.getId());
			log.info("Delete quiz with id {}", quiz.getId());
			quizService.deleteQuiz(quiz.getId());
		});

		log.info("Delete superuser with id {}", superuserId);
		superuserService.deleteSuperuser(superuserId);
		return "redirect:/users/professors-companies";
	}

	@PostMapping("/edit-student/{studentId}")
	public String editStudent(@ModelAttribute("student") Student student, @PathVariable("studentId") final String id) {
		var educationInfo = student.getProgram().split(",");
		student.setProgram(educationInfo[0]);
		student.setDomain(educationInfo[1]);
		student.setYear(Integer.parseInt(educationInfo[2]));
		student.setId(id);
		if (studentService.isDuplicateExcept(student.getEmail(), student.getId())) {
			return "redirect:/users/students?duplicate=true";
		} else {
			var oldStudent = studentService.getStudentById(id);
			student.setPassword(oldStudent.getPassword());
			student.setResetCode(oldStudent.getResetCode());
			studentService.saveStudent(student);
		}
		return "redirect:/users/students";
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

	@PostMapping("/edit-superuser/{superuserId}")
	public String editSuperuser(@ModelAttribute("superuser") Superuser superuser, @PathVariable("superuserId") final String id) {
		superuser.setId(id);
		if (superuserService.isDuplicateExcept(superuser.getEmail(), superuser.getId())) {
			return "redirect:/users/professors-companies?duplicate=true";
		} else {
			var oldSuperuser = superuserService.getSuperuserById(id);
			superuser.setPassword(oldSuperuser.getPassword());
			superuser.setResetCode(oldSuperuser.getResetCode());
			superuserService.saveSuperuser(superuser);
		}
		return "redirect:/users/professors-companies";
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
