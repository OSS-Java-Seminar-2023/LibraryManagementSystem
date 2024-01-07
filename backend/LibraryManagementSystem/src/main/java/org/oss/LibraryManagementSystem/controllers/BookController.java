package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.models.File;
import org.oss.LibraryManagementSystem.models.enums.BookStatus;
import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.oss.LibraryManagementSystem.services.BookService;
import org.oss.LibraryManagementSystem.services.FileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final FileService fileService;
    private final BookInfoRepository bookInfoRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String getAllBooksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        Long count = bookService.getBookCount();

        var statusOptions = BookStatus.values();

        List<Category> categoryOptions = categoryRepository.findAll();

        // Make array of all files then get element from that array that coresponds to file of book

        model.addAttribute("books", books);
        model.addAttribute("count", count);

        model.addAttribute("statusOptions", statusOptions);
        model.addAttribute("categoryOptions", categoryOptions);
        return "book/allBooks";
    }

    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable UUID id, Model model) {
        Book book = bookService.getBook(id);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(book.getDateOfPublishing());

        // Get file by fileId then display it if its not null
        if(book.getFile() != null) {
            File fileImage = book.getFile();
            var imageId = fileImage.getId();

            // Add model atribute
            model.addAttribute("fileImage", imageId);
        }

        model.addAttribute("book", book);
        model.addAttribute("dateOfPublishing", formattedDate);
        return "book/bookDetails";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewBookPage(Model model, BookDto bookDto) {
        // Get book infos to display as options
        List<BookInfo> bookInfos = bookInfoRepository.findAll();
        // Get statuses to display as options
        List<BookStatus> bookStatuses = Arrays.stream(BookStatus.values()).toList();

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("bookInfoOptions", bookInfos);
        model.addAttribute("bookStatusOptions", bookStatuses);
        return "book/addNewBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute BookDto bookDto, @RequestParam("file") MultipartFile file) throws IOException {
        File fileDb = fileService.store(file);
//        System.out.println(fileDb.getId());

        // Add fileId to bookDto
        bookDto.setFileId(fileDb.getId());
        Book savedBook = bookService.createBook(bookDto);
        return "redirect:/books";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(id);
            redirectAttributes.addFlashAttribute("message", "The book with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/books";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/edit/{id}")
    public String editBookPage(@PathVariable UUID id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        // Get book infos to display as options
        List<BookInfo> bookInfos = bookInfoRepository.findAll();
        // Get statuses to display as options
        List<BookStatus> bookStatuses = Arrays.stream(BookStatus.values()).toList();

        // Format date for input field
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(book.getDateOfPublishing());

        model.addAttribute("bookInfoOptions", bookInfos);
        model.addAttribute("bookStatusOptions", bookStatuses);
        model.addAttribute("book", book);
        model.addAttribute("dateOfPublishing", formattedDate);
        return "book/editBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute BookDto bookDto){
        Book book = bookService.editBook(bookDto);

        return "redirect:/books";
    }
}
