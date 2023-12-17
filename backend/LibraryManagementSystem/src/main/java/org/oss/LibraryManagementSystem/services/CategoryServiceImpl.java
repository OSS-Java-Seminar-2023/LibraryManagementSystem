package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();

        category.setName(categoryDto.getName());
        return categoryRepository.save(category);
    }

    @Override
    public String deleteCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
        return "Category with id " + id + " has been deleted.";
    }

    @Override
    public Category editCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return categoryRepository.save(category);
    }
}
