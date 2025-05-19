package com.example.professor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.professor.model.CategoryInfo;
import com.example.professor.model.CategoryWithSkills;
import com.example.professor.repository.CategoryRepository;
import com.example.professor.repository.SkillRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    final SkillRepository skillRepository;
    final CategoryRepository categoryRepository;

    public List<CategoryWithSkills> getAllCategoriesAndSkills() {
        return categoryRepository.findAll().stream()
            .map(category -> {
                var categoryWithSkills = new CategoryWithSkills();
                var categoryInfo = new CategoryInfo();
                categoryInfo.setDescription(category.getDescription());
                categoryInfo.setHasDescription(!category.getDescription().isEmpty());
                categoryInfo.setName(category.getName());
                categoryInfo.setId(category.getId());
                var skills = skillRepository.findByCategoryId(category.getId()).stream().sorted().toList();
                categoryWithSkills.setCategory(categoryInfo);
                categoryWithSkills.setSkills(skills);
                return categoryWithSkills;
            })
            .filter(categoryWithSkills -> !categoryWithSkills.getSkills().isEmpty())
            .sorted()
            .toList();
    }

}
