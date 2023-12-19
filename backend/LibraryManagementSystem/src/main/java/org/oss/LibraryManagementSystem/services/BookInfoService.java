package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.models.BookInfo;

import java.util.List;
import java.util.UUID;

public interface BookInfoService {
    List<BookInfo> getAllBookInfos();

    BookInfo createBookInfo(BookInfoDto bookInfoDto);

    String deleteBookInfoById(UUID id);

    BookInfo editBookInfo(BookInfoDto bookInfoDto);

    Long getBookInfoCount();
}
