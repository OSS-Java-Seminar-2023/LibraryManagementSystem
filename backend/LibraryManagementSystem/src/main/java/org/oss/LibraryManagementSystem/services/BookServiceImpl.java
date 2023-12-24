package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.enums.BookStatus;
import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookInfoRepository bookInfoRepository;

    public BookServiceImpl(BookRepository bookRepository, BookInfoRepository bookInfoRepository) {
        this.bookRepository = bookRepository;
        this.bookInfoRepository = bookInfoRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(BookDto bookDto) {
        Book book = new Book();

        book.setPublisherName(bookDto.getPublisherName());
        book.setYearOfPublishing(bookDto.getYearOfPublishing());
        book.setIsbn(bookDto.getIsbn());

        String bookStatusString = bookDto.getBookStatus();
        BookStatus bookStatus = BookStatus.valueOf(bookStatusString);
        book.setBookStatus(bookStatus);

        BookInfo bookInfo = bookInfoRepository.findById(bookDto.getBookInfo()).orElseThrow(() -> new RuntimeException("Book information not found"));

        book.setBookInfo(bookInfo);
        book.setAvailable(true);

        return bookRepository.save(book);
    }

    @Override
    public String deleteBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
        return "Book with id " + id + " has been deleted.";
    }

    @Override
    public Book editBook(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new RuntimeException("Book not found"));

        book.setPublisherName(bookDto.getPublisherName());
        book.setYearOfPublishing(bookDto.getYearOfPublishing());
        book.setIsbn(bookDto.getIsbn());

        String bookStatusString = bookDto.getBookStatus();
        BookStatus bookStatus = BookStatus.valueOf(bookStatusString);
        book.setBookStatus(bookStatus);

        BookInfo bookInfo = bookInfoRepository.findById(bookDto.getBookInfo()).orElseThrow(() -> new RuntimeException("Book information not found"));

        book.setBookInfo(bookInfo);
        book.setAvailable(true);

        return bookRepository.save(book);
    }

    @Override
    public Long getBookCount() {
        return bookRepository.count();
    }
}
