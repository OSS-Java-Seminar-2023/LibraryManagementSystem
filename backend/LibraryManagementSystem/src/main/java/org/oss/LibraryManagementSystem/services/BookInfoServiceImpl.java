package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.models.*;
import org.oss.LibraryManagementSystem.repositories.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookInfoServiceImpl implements BookInfoService {
    private final BookInfoRepository bookInfoRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;


    @Override
    public List<BookInfo> getAllBookInfos() {
        return bookInfoRepository.findAll();
    }

    @Override
    public BookInfo createBookInfo(BookInfoDto bookInfoDto) {
        BookInfo bookInfo = new BookInfo();

        bookInfo.setTitle(bookInfoDto.getTitle());
        bookInfo.setDescription(bookInfoDto.getDescription());

        Set<Author> bookInfoAuthors = new HashSet<>();
        Set<Category> bookInfoCategories = new HashSet<>();

        Set<UUID> authorsSet = bookInfoDto.getAuthors();
        Set<UUID> categoriesSet = bookInfoDto.getCategories();

        // Add authors from authorSet to bookInfoAuthors
        if(authorsSet != null) {
            for (UUID authorId : authorsSet) {
                Author author = authorRepository.findById(authorId).orElse(null);
                bookInfoAuthors.add(author);
            }
        }

        // Add categories from categoriesSet to bookInfoCategoriews
        if(categoriesSet != null) {
            for (UUID categoryId : categoriesSet) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                bookInfoCategories.add(category);
            }
        }

        bookInfo.setAuthors(bookInfoAuthors);
        bookInfo.setCategories(bookInfoCategories);

        return bookInfo;
    }

    @Override
    public String deleteBookInfoById(UUID id) {
        BookInfo bookInfo = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book info not found"));

        // Find all books linked to book info (using book repository custom query)
        List<Book> books = bookRepository.findBooksByBookInfoId(bookInfo.getId());
        System.out.println(books);

        // Loop thru books
        for(Book book : books) {
            // check if file is present
            if(book.getFile() != null) {
                // Get file from book
                File fileFromBook = book.getFile();

                // If it is delete reference to that file in book
                book.setFile(null);

                // Delete file from database
                fileRepository.delete(fileFromBook);
            }
        }

        bookInfoRepository.delete(bookInfo);
        return "Book info with id " + id + " has been deleted.";
    }

    @Override
    public BookInfo editBookInfo(BookInfoDto bookInfoDto) {
        BookInfo bookInfo = bookInfoRepository.findById(bookInfoDto.getId()).orElseThrow(() -> new RuntimeException("Book info not found"));

        bookInfo.setId(bookInfoDto.getId());
        bookInfo.setTitle(bookInfoDto.getTitle());
        bookInfo.setDescription(bookInfoDto.getDescription());

        Set<Author> bookInfoAuthors = new HashSet<>();
        Set<Category> bookInfoCategories = new HashSet<>();

        Set<UUID> authorsSet = bookInfoDto.getAuthors();
        Set<UUID> categoriesSet = bookInfoDto.getCategories();

        // Add authors from authorSet to bookInfoAuthors
        if(authorsSet != null) {
            for (UUID authorId : authorsSet) {
                Author author = authorRepository.findById(authorId).orElse(null);
                bookInfoAuthors.add(author);
            }
        }

        // Add categories from categoriesSet to bookInfoCategoriews
        if(categoriesSet != null) {
            for (UUID categoryId : categoriesSet) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                bookInfoCategories.add(category);
            }
        }

        bookInfo.setAuthors(bookInfoAuthors);
        bookInfo.setCategories(bookInfoCategories);

        return bookInfoRepository.save(bookInfo);
    }

    @Override
    public Long getBookInfoCount() {
        return bookInfoRepository.count();
    }
}
