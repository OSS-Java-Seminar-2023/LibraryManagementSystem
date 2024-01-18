package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findAll(Pageable pageable);

    // Find by name
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Category findByNameContainingIgnoreCase(String name);
}
