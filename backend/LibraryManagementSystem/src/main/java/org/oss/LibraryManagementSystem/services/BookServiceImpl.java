package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.mapper.BookMapper;
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
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookInfoRepository bookInfoRepository;
    private final FileRepository fileRepository;

    private final BookMapper bookMapper;

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
        Book book = bookMapper.bookDtoToBook(bookDto);

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
    public String deleteBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
        return "Book with id " + id + " has been deleted.";
    }

    @Override
    public Book editBook(BookDto bookDto) {
        Book foundBook = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new RuntimeException("Book not found"));

        Book book = bookMapper.bookDtoToBook(bookDto);

        book.setId(foundBook.getId()); // Preserve current entity id

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
