package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BookService {

    Page<Book> getAllBooks(int page, int size, String sortField, String sortDirection);

    Page<Book> getBooksByBookInformation(UUID id, int page, int size, String sortField, String sortDirection);

    Book getBook(UUID id);

    Book createBook(BookDto bookDto);

    String deleteBookById(UUID id);

    Book editBook(BookDto bookDto);

    Long getBookCount();
}
