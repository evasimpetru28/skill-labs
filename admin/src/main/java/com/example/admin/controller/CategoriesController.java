package com.example.admin.controller;

import com.example.admin.entity.Category;
import com.example.admin.entity.Page;
import com.example.admin.service.AdminService;
import com.example.admin.service.CategoryService;
import com.example.admin.service.NavbarService;
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
public class CategoriesController {

	final AdminService adminService;
	final NavbarService navbarService;
	final CategoryService categoryService;

	@GetMapping("/categories/{adminId}")
	String getCategoriesPage(Model model, @PathVariable String adminId, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.CATEGORIES, model);
		model.addAttribute("categoryList", categoryService.getCategoryModelList());
		model.addAttribute("duplicate", duplicate);
		model.addAttribute("adminId", adminId);
		model.addAttribute("name", adminService.getAdminById(adminId).getName());
		return "categories";
	}

	@PostMapping("/add-category/{adminId}")
	public String addCategory(@ModelAttribute("category") Category category, @PathVariable String adminId) {
		if (categoryService.isDuplicate(category.getName())) {
			return "redirect:/categories/" + adminId + "?duplicate=true";
		} else {
			categoryService.saveCategory(category);
		}

		return "redirect:/categories/" + adminId;
	}

	@PostMapping("/delete-category/{categoryId}/{adminId}")
	public String deleteCategory(@PathVariable String categoryId, @PathVariable String adminId) {
		// TODO: Delete all related, skills, evalations, quizzez
		categoryService.deleteCategory(categoryId);
		return "redirect:/categories/" + adminId;
	}

	@PostMapping("/edit-category/{id}/{adminId}")
	public String editCategory(@ModelAttribute("category") Category category, @PathVariable("id") final String id, @PathVariable String adminId) {
		category.setId(id);
		if (categoryService.isDuplicateExcept(category.getName(), category.getId())) {
			return "redirect:/categories/" + adminId + "?duplicate=true";
		} else {
			categoryService.saveCategory(category);
		}
		return "redirect:/categories/" + adminId;
	}

	@PostMapping("/import-categories/{adminId}")
	public String importCategories(@PathVariable String adminId, @RequestParam("file") MultipartFile file) {
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
				Cell descriptionCell = row.getCell(1);

				if (nameCell != null) {
					String name = nameCell.getStringCellValue().trim();
					String description = descriptionCell != null ? descriptionCell.getStringCellValue().trim() : "";

					if (!name.isEmpty() && !categoryService.isDuplicate(name) && description.length() <= 255) {
						Category category = new Category();
						category.setName(name);
						category.setDescription(description);
						categoryService.saveCategory(category);
					}
				}
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/categories/" + adminId;
	}
}
