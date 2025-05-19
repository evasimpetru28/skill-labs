package com.example.admin.controller;

import com.example.admin.entity.Category;
import com.example.admin.entity.Page;
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

	final NavbarService navbarService;
	final CategoryService categoryService;

	@GetMapping("/categories")
	String getCategoriesPage(Model model, @RequestParam(required = false) final Boolean duplicate) {
		navbarService.activateNavbarTab(Page.CATEGORIES, model);
		model.addAttribute("categoryList", categoryService.getCategoryModelList());
		model.addAttribute("duplicate", duplicate);
		return "categories";
	}

	@PostMapping("/add-category")
	public String addCategory(@ModelAttribute("category") Category category) {
		if (categoryService.isDuplicate(category.getName())) {
			return "redirect:/categories?duplicate=true";
		} else {
			categoryService.saveCategory(category);
		}

		return "redirect:/categories";
	}

	@PostMapping("/delete-category/{categoryId}")
	public String deleteCategory(@PathVariable String categoryId) {
		// Delete all related, skills, evalations, quizzez
		categoryService.deleteCategory(categoryId);
		return "redirect:/categories";
	}

	@PostMapping("/edit-category/{id}")
	public String editCategory(@ModelAttribute("category") Category category, @PathVariable("id") final String id) {
		category.setId(id);
		if (categoryService.isDuplicateExcept(category.getName(), category.getId())) {
			return "redirect:/categories?duplicate=true";
		} else {
			categoryService.saveCategory(category);
		}
		return "redirect:/categories";
	}

	@PostMapping("/import-categories")
	public String importCategories(@RequestParam("file") MultipartFile file) {
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			List<Category> categories = new ArrayList<>();
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
						categories.add(category);
					}
				}
			}

			workbook.close();
			categories.forEach(categoryService::saveCategory);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/categories";
	}
}
