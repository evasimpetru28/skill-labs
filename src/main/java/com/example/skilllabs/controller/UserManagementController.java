package com.example.skilllabs.controller;

import com.example.skilllabs.entity.Page;
import com.example.skilllabs.entity.Student;
import com.example.skilllabs.service.NavbarService;
import com.example.skilllabs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserManagementController {

    final NavbarService navbarService;
    final StudentService studentService;

    @GetMapping("/users")
    String getUserManagementPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
        navbarService.activateNavbarTab(Page.USER_MANAGEMENT, model);
        model.addAttribute("studentList", studentService.getStudentModelList());
        model.addAttribute("duplicate", duplicate);
        return "user-management";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute("user")Student student) {
        var educationInfo = student.getProgram().split(",");
        student.setProgram(educationInfo[0]);
        student.setDomain(educationInfo[1]);
        student.setYear(Integer.parseInt(educationInfo[2]));

        if (studentService.isDuplicate(student.getName())) {
            return "redirect:/users?duplicate=true";
        } else {
            studentService.saveStudent(student);
        }

        return "redirect:/users";
    }

    @PostMapping("/delete-user/{studentId}")
    public String deleteCategory(@PathVariable String studentId) {
        // Delete all evalations, quizzez
        studentService.deleteStudent(studentId);
        return "redirect:/users";
    }

    @PostMapping("/edit-user/{id}")
    public String editCategory(@ModelAttribute("user") Student student, @PathVariable("id") final String id) {
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
}
