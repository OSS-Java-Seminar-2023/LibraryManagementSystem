package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.services.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookInfoController {
    private BookInfoService bookInfoService;

    @Autowired
    public BookInfoController(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }
}
