package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategory(CategoryDto categoryDto);

    String deleteCategoryById(UUID id);

    Category editCategory(CategoryDto categoryDto);
}
