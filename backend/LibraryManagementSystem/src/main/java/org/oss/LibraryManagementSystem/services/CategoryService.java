package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Page<Category> getAllCategories(String searchQuery, int page, int size, String sortField, String sortDirection);

    Category createCategory(CategoryDto categoryDto);

    String deleteCategoryById(UUID id);

    Category editCategory(CategoryDto categoryDto);

    Long getCategoryCount();
}
