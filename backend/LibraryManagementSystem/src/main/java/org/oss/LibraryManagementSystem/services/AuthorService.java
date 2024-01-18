package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.AuthorDto;
import org.oss.LibraryManagementSystem.models.Author;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    Page<Author> getAllAuthors(String searchQuery, int page, int size, String sortField, String sortDirection);

    Author createAuthor(AuthorDto authorDto);

    String deleteAuthorById(UUID id);

    Author editAuthor(AuthorDto authorDto);

    Long getAuthorCount();
}
