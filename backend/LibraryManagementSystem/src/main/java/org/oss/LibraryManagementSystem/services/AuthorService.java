package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
