package com.example.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MySkillsController {

	@GetMapping("/my-skills")
	public String getMySkillsPage() {
		return "my-skills";
	}

}
