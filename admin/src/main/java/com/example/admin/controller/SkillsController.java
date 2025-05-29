package com.example.admin.controller;

import com.example.admin.entity.Page;
import com.example.admin.entity.Skill;
import com.example.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SkillsController {

	final AdminService adminService;
	final SkillService skillService;
	final NavbarService navbarService;
	final CategoryService categoryService;
	final EvaluationService evaluationService;

	@GetMapping("/skills/{adminId}")
	String getSkillsPage(Model model, @PathVariable String adminId, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.SKILLS, model);
		model.addAttribute("categoryList", categoryService.getCategoryModelList());
		model.addAttribute("skillList", skillService.getSkillModelList());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("adminId", adminId);
		model.addAttribute("name", adminService.getAdminById(adminId).getName());
		return "skills";
	}

	@PostMapping("/add-skill/{adminId}")
	public String addSkill(@ModelAttribute("skill") Skill skill, @PathVariable String adminId) {
		if (skillService.isDuplicate(skill.getName())) {
			return "redirect:/skills?duplicate=true";
		} else {
			skillService.saveSkill(skill);
		}
		return "redirect:/skills/" + adminId;
	}

	@PostMapping("/delete-skill/{skillId}/{adminId}")
	public String deleteSkill(@PathVariable final String skillId, @PathVariable String adminId) {
		evaluationService.deleteAllEvaluationsForSkill(skillId);
		skillService.deleteSkill(skillId);
		return "redirect:/skills/" + adminId;
	}

	@PostMapping("/edit-skill/{id}/{adminId}")
	public String editSkill(@ModelAttribute("skill") Skill skill, @PathVariable("id") final String id, @PathVariable String adminId) {
		skill.setId(id);
		if (skillService.isDuplicateExcept(skill.getName(), skill.getId())) {
			return "redirect:/skills?duplicate=true";
		} else {
			skillService.saveSkill(skill);
		}
		return "redirect:/skills/" + adminId;
	}

	@PostMapping("/import-skills/{adminId}")
	public String importSkills(@PathVariable String adminId, @RequestParam("file") MultipartFile file) {
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			boolean isFirstRow = true;

			for (Row row : sheet) {
				if (isFirstRow) {
					isFirstRow = false;
					continue;
				}

				Cell nameCell = row.getCell(0);
				Cell categoryCell = row.getCell(1);
				Cell descriptionCell = row.getCell(2);

				if (nameCell != null && categoryCell != null) {
					String name = nameCell.getStringCellValue().trim();
					String categoryName = categoryCell.getStringCellValue().trim();
					String description = descriptionCell != null ? descriptionCell.getStringCellValue().trim() : "";

					// Skip if skill exists or category doesn't exist
					var category = categoryService.getCategoryByName(categoryName.toLowerCase());
					if (!name.isEmpty() && !skillService.isDuplicate(name) && category != null && description.length() <= 255) {
						Skill skill = new Skill();
						skill.setName(name);
						skill.setCategoryId(category.getId());
						skill.setDescription(description);
						skillService.saveSkill(skill);
					}
				}
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/skills/" + adminId;
	}
}
