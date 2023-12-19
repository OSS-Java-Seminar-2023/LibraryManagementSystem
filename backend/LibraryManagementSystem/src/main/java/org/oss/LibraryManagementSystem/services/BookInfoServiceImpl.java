package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookInfoServiceImpl implements BookInfoService {
    private final BookInfoRepository bookInfoRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookInfoServiceImpl(BookInfoRepository bookInfoRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

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
        for (UUID authorId : authorsSet) {
            Author author = authorRepository.findById(authorId).orElse(null);
            bookInfoAuthors.add(author);
        }

        // Add categories from categoriesSet to bookInfoCategoriews
        for (UUID categoryId : categoriesSet) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            bookInfoCategories.add(category);
        }

        bookInfo.setAuthors(bookInfoAuthors);
        bookInfo.setCategories(bookInfoCategories);

        return bookInfo;
    }

    @Override
    public String deleteBookInfoById(UUID id) {
        BookInfo bookInfo = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book info not found"));

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
        for (UUID authorId : authorsSet) {
            Author author = authorRepository.findById(authorId).orElse(null);
            bookInfoAuthors.add(author);
        }

        // Add categories from categoriesSet to bookInfoCategoriews
        for (UUID categoryId : categoriesSet) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            bookInfoCategories.add(category);
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
