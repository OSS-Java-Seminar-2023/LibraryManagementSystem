package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BookInfoService {
    Page<BookInfo> getAllBookInfos(String searchQuery, String categoryName, int page, int size, String sortField, String sortDirection);

    BookInfo createBookInfo(BookInfoDto bookInfoDto);

    String deleteBookInfoById(UUID id);

    BookInfo editBookInfo(BookInfoDto bookInfoDto);

    Long getBookInfoCount();
}
