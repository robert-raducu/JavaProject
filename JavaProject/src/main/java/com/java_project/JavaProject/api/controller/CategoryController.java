package com.java_project.JavaProject.api.controller;

import com.java_project.JavaProject.api.dto.categoryDto.AddCategoryDto;
import com.java_project.JavaProject.domain.category.Category;
import com.java_project.JavaProject.domain.category.CategoryRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/addCategory")
    Category addCategory(@RequestBody AddCategoryDto addDto){
        Category newCategory = new Category();

        newCategory.setName(addDto.getName());

        return categoryRepository.save(newCategory);
    }
}
