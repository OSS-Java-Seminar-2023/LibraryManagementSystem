package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Page<Author> findAll(Pageable pageable);

    // Search by first name or last name
    Page<Author> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String firstName, String lastName, Pageable pageable);
}
