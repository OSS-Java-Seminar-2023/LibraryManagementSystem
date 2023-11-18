package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class BookInfoServiceImpl implements BookInfoService {
    private final BookInfoRepository bookInfoRepository;

    public BookInfoServiceImpl(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }
}
