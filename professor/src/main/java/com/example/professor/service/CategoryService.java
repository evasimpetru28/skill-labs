package com.example.professor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.professor.dto.CategoryInfoDto;
import com.example.professor.dto.CategoryWithSkillsDto;
import com.example.professor.repository.CategoryRepository;
import com.example.professor.repository.SkillRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    final SkillRepository skillRepository;
    final CategoryRepository categoryRepository;

    public List<CategoryWithSkillsDto> getAllCategoriesAndSkills(String superuserId) {
        return categoryRepository.findAll().stream()
            .map(category -> {
                var categoryWithSkills = new CategoryWithSkillsDto();
                var categoryInfo = new CategoryInfoDto();
                categoryInfo.setDescription(category.getDescription());
                categoryInfo.setHasDescription(!category.getDescription().isEmpty());
                categoryInfo.setName(category.getName());
                categoryInfo.setId(category.getId());
                var skills = skillRepository.findByCategoryId(category.getId(), superuserId).stream().sorted().toList();
                categoryWithSkills.setCategory(categoryInfo);
                categoryWithSkills.setSkills(skills);
                return categoryWithSkills;
            })
            .filter(categoryWithSkillsDto -> !categoryWithSkillsDto.getSkills().isEmpty())
            .sorted()
            .toList();
    }

    public List<CategoryWithSkillsDto> getAllBookmarkedCategoriesAndSkills(String superuserId) {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    var categoryWithSkills = new CategoryWithSkillsDto();
                    var categoryInfo = new CategoryInfoDto();
                    categoryInfo.setDescription(category.getDescription());
                    categoryInfo.setHasDescription(!category.getDescription().isEmpty());
                    categoryInfo.setName(category.getName());
                    categoryInfo.setId(category.getId());
                    var skills = skillRepository.findBookmarkedByCategoryId(category.getId(), superuserId).stream().sorted().toList();
                    categoryWithSkills.setCategory(categoryInfo);
                    categoryWithSkills.setSkills(skills);
                    return categoryWithSkills;
                })
                .filter(categoryWithSkillsDto -> !categoryWithSkillsDto.getSkills().isEmpty())
                .sorted()
                .toList();
    }

}
