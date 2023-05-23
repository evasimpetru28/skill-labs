package com.example.admin.controller;

import com.example.admin.entity.Category;
import com.example.admin.entity.Page;
import com.example.admin.service.CategoryService;
import com.example.admin.service.NavbarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
