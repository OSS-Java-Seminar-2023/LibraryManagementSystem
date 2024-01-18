package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookInfoRepository extends JpaRepository<BookInfo, UUID> {
    Page<BookInfo> findAll(Pageable pageable);

    // Find by title
    Page<BookInfo> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Find by categories
    Page<BookInfo> findByCategoriesContainingIgnoreCase(Category category, Pageable pageable);

    // Find by title and categories
    Page<BookInfo> findByTitleContainingIgnoreCaseAndCategoriesContainingIgnoreCase(String title, Category category, Pageable pageable);
}
