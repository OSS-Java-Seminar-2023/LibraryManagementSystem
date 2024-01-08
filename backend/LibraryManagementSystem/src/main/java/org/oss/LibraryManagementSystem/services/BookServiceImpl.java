package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.File;
import org.oss.LibraryManagementSystem.models.enums.BookStatus;
import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookInfoRepository bookInfoRepository;

    private final FileRepository fileRepository;

    public BookServiceImpl(BookRepository bookRepository, BookInfoRepository bookInfoRepository, FileRepository fileRepository) {
        this.bookRepository = bookRepository;
        this.bookInfoRepository = bookInfoRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book createBook(BookDto bookDto) {
        Book book = new Book();

        book.setPublisherName(bookDto.getPublisherName());
        book.setDateOfPublishing(bookDto.getDateOfPublishing());
        book.setIsbn(bookDto.getIsbn());
        book.setImage(bookDto.getImage());

        String bookStatusString = bookDto.getBookStatus();
        BookStatus bookStatus = BookStatus.valueOf(bookStatusString);
        System.out.println(bookStatus);
        book.setBookStatus(bookStatus);

        BookInfo bookInfo = bookInfoRepository.findById(bookDto.getBookInfo()).orElseThrow(() -> new RuntimeException("Book information not found"));

        book.setBookInfo(bookInfo);

        boolean isAvailable = bookDto.getAvailable();
        book.setAvailable(isAvailable);

        // Get file with id
        if(bookDto.getFileId() != null) {
            File fileFromDb = fileRepository.findById(bookDto.getFileId()).orElseThrow(() -> new RuntimeException("File not found"));;
            book.setFile(fileFromDb);
        }

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

        book.setId(bookDto.getId());
        book.setPublisherName(bookDto.getPublisherName());
        book.setDateOfPublishing(bookDto.getDateOfPublishing());
        book.setIsbn(bookDto.getIsbn());
        book.setImage(bookDto.getImage());

        String bookStatusString = bookDto.getBookStatus();
        BookStatus bookStatus = BookStatus.valueOf(bookStatusString);
        book.setBookStatus(bookStatus);

        BookInfo bookInfo = bookInfoRepository.findById(bookDto.getBookInfo()).orElseThrow(() -> new RuntimeException("Book information not found"));

        book.setBookInfo(bookInfo);

        boolean isAvailable = bookDto.getAvailable();
        book.setAvailable(isAvailable);

        // Get file with id
        if(bookDto.getFileId() != null) {
            File fileFromDb = fileRepository.findById(bookDto.getFileId()).orElseThrow(() -> new RuntimeException("File not found"));;
            book.setFile(fileFromDb);
        } else {
            book.setFile(null);
        }

        return bookRepository.save(book);
    }

    @Override
    public Long getBookCount() {
        return bookRepository.count();
    }
}
