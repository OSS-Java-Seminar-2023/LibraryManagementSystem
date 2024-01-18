package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.mapper.CategoryMapper;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Page<Category> getAllCategories(String searchQuery, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        if (searchQuery != null) {
            return categoryRepository.findByNameContainingIgnoreCase(searchQuery, paging);
        } else {
            return categoryRepository.findAll(paging);
        }
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDtoToCategory(categoryDto);
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

        Category editedCategory = categoryMapper.categoryDtoToCategory(categoryDto);
        editedCategory.setId(category.getId()); // Preserve current entity with its id
        return categoryRepository.save(editedCategory);
    }

    @Override
    public Long getCategoryCount() {
        return categoryRepository.count();
    }
}
