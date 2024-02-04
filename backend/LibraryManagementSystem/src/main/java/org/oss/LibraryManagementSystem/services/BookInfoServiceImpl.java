package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.mapper.BookInfoMapper;
import org.oss.LibraryManagementSystem.models.*;
import org.oss.LibraryManagementSystem.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final BookInfoMapper bookInfoMapper;

    @Override
    public Page<BookInfo> getAllBookInfos(String searchQuery, String categoryName, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        Category category;

        if (searchQuery != null && categoryName != null && !categoryName.equals("All categories")) {
            category = categoryRepository.findByNameContainingIgnoreCase(categoryName);
            return bookInfoRepository.findByTitleContainingIgnoreCaseAndCategoriesContainingIgnoreCase(searchQuery, category, paging);

        } else if (categoryName != null && !categoryName.equals("All categories")) {
            category = categoryRepository.findByNameContainingIgnoreCase(categoryName);
            return bookInfoRepository.findByCategoriesContainingIgnoreCase(category, paging);

        } else if (searchQuery != null) {
            return bookInfoRepository.findByTitleContainingIgnoreCase(searchQuery, paging);
        } else {
            return bookInfoRepository.findAll(paging);
        }
    }

    @Override
    public BookInfo createBookInfo(BookInfoDto bookInfoDto) {
        BookInfo bookInfo = bookInfoMapper.bookInfoDtoToBookInfo(bookInfoDto);

        return bookInfoRepository.save(bookInfo);
    }

    @Override
    public String deleteBookInfoById(UUID id) {
        BookInfo bookInfo = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book info not found"));

        // Find all books linked to book info
        List<Book> books = bookRepository.findBooksByBookInfoId(bookInfo.getId());

        // Loop thru books
        books.stream().forEach(book -> {
            if(book.getFile() != null) {
                // Get file from book
                File fileFromBook = book.getFile();

                // If it is delete reference to that file in book
                book.setFile(null);

                // Delete file from database
                fileRepository.delete(fileFromBook);
            }
        });

        bookInfoRepository.delete(bookInfo);
        return "Book info with id " + id + " has been deleted.";
    }

    @Override
    public BookInfo editBookInfo(BookInfoDto bookInfoDto) {
        BookInfo bookInfo = bookInfoRepository.findById(bookInfoDto.getId()).orElseThrow(() -> new RuntimeException("Book info not found"));

        BookInfo editedBookInfo = bookInfoMapper.bookInfoDtoToBookInfo(bookInfoDto);

        editedBookInfo.setId(bookInfo.getId()); // Preserve current entity id

        return bookInfoRepository.save(editedBookInfo);
    }

    @Override
    public Long getBookInfoCount() {
        return bookInfoRepository.count();
    }
}
