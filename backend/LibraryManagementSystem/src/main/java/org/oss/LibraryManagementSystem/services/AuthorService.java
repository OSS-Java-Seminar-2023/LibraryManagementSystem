package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.AuthorDto;
import org.oss.LibraryManagementSystem.models.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    List<Author> getAllAuthors();

    Author createAuthor(AuthorDto authorDto);

    String deleteAuthorById(UUID id);

    Author editAuthor(AuthorDto authorDto);

    Long getAuthorCount();
}
