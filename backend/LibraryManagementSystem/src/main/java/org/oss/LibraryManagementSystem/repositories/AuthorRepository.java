package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
