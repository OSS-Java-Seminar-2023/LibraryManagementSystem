package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BookService {

    Page<Book> getAllBooks(String searchQuery, String statusName, String categoryName, int page, int size);

    Page<Book> getBooksByBookInformation(UUID id, String searchQuery, String statusName, int page, int size);

    Book getBook(UUID id);

    Book createBook(BookDto bookDto);

    String deleteBookById(UUID id);

    Book editBook(BookDto bookDto);

    Long getBookCount();
}
