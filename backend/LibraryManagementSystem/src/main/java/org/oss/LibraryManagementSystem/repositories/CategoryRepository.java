package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
