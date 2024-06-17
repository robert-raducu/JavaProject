package com.java_project.JavaProject.api.controller;

import com.java_project.JavaProject.api.dto.categoryDto.AddCategoryDto;
import com.java_project.JavaProject.api.dto.categoryDto.UpdateCategoryDto;
import com.java_project.JavaProject.domain.category.Category;
import com.java_project.JavaProject.domain.category.CategoryRepository;
import com.java_project.JavaProject.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/CategoryPage")
public class CategoryController {

    final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String userString(){
        return "Welcome to the categories page!";
    }

    @GetMapping("/categories")
    public List<Category> getallCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    Category getAllCategories(@PathVariable Integer id){
        return categoryRepository.findById(id).get();
    }

    @PostMapping("/addCategory")
    Category addCategory(@RequestBody AddCategoryDto addDto){
        Category newCategory = new Category();

        newCategory.setName(addDto.getName());

        return categoryRepository.save(newCategory);
    }

    @PatchMapping("/updateCategory/{id}")
    Category updateCategory(
            @PathVariable Integer id,
            @RequestBody UpdateCategoryDto updateDto
            ){
        Category updatedCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No category" +
                        "available for this id: " + id));

        if (!Objects.equals(updateDto.getName(), "string")) {
            updatedCategory.setName(updateDto.getName());
        }

        return categoryRepository.save(updatedCategory);
    }

    @DeleteMapping("/deleteCategory/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable Integer id){
        Category deletedCategory = categoryRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No category" +
                        "available for this id: " + id));

        categoryRepository.delete(deletedCategory);
        return ResponseEntity.ok("The category was successfully deleted!");
    }
}
