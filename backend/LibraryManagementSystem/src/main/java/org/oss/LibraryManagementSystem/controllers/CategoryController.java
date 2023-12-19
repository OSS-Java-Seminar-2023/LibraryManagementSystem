package org.oss.LibraryManagementSystem.controllers;

import com.github.javafaker.Cat;
import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.services.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllCategoriesPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        Long count = categoryRepository.count();

        model.addAttribute("categories", categories);
        model.addAttribute("count", count);
        return "category/allCategories";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewCategoryPage(Model model, CategoryDto categoryDto) {
        model.addAttribute("categoryDto", categoryDto);
        return "category/addNewCategory";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute CategoryDto categoryDto) {
        Category savedCategory = categoryService.createCategory(categoryDto);
        return "redirect:/categories";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("message", "The category with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/categories";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/edit/{id}")
    public String editCategoryPage(@PathVariable UUID id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        model.addAttribute("category", category);
        return "category/editCategory";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute CategoryDto categoryDto) {
        Category category = categoryService.editCategory(categoryDto);

        return "redirect:/categories";
    }
}
