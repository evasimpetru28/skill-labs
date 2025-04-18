package com.example.admin.service;

import com.example.admin.entity.Category;
import com.example.admin.model.CategoryModel;
import com.example.admin.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

	final CategoryRepository categoryRepository;

	public void saveCategory(Category category) {
		categoryRepository.saveAndFlush(category);
	}

	public void deleteCategory(String categoryId) {
		categoryRepository.deleteById(categoryId);
	}

	public Boolean isDuplicate(String name) {
		return categoryRepository.existsByNameIgnoreCase(name);
	}

	public Boolean isDuplicateExcept(String name, String id) {
		return categoryRepository.existsByNameIgnoreCase(name, id);
	}

	public List<CategoryModel> getCategoryModelList() {
		var categories = getAllCategories();
		var index = new AtomicInteger(1);
		return categories.stream()
				.map(category -> new CategoryModel(
						category.getId(),
						index.getAndIncrement(),
						category.getName(),
						category.getDescription()))
				.toList();
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAllByOrderByName();
	}

	public Category getCategoryById(String id) {
		return categoryRepository.getReferenceById(id);
	}

	public Category getCategoryByName(String name) {
		return categoryRepository.findByNameIgnoreCase(name).orElse(null);
	}

}
