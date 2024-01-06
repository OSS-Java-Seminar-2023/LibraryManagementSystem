package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<Book> getAllBooks();

    Book getBook(UUID id);

    Book createBook(BookDto bookDto);

    String deleteBookById(UUID id);

    Book editBook(BookDto bookDto);

    Long getBookCount();
}
