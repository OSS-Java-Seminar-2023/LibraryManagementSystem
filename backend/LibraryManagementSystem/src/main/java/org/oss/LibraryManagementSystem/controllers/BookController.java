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
import org.oss.LibraryManagementSystem.repositories.FileRepository;
import org.oss.LibraryManagementSystem.services.BookService;
import org.oss.LibraryManagementSystem.services.FileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final FileRepository fileRepository;

    @GetMapping
    public String getAllBooksPage(Model model,
                                  @RequestParam(required = false) String searchQuery,
                                  @RequestParam(required = false) String statusName,
                                  @RequestParam(required = false) String categoryName,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "3") int size
    ) {
        var pageBooks = bookService.getAllBooks(searchQuery, statusName, categoryName, page, size);
        var books = pageBooks.getContent();
        Long count = bookService.getBookCount();

        var statusOptions = BookStatus.values();

        List<Category> categoryOptions = categoryRepository.findAll();

        model.addAttribute("books", books);
        model.addAttribute("count", count);

        model.addAttribute("statusOptions", statusOptions);
        model.addAttribute("categoryOptions", categoryOptions);

        model.addAttribute("currentPage", pageBooks.getNumber() + 1);
        model.addAttribute("totalItems", pageBooks.getTotalElements());
        model.addAttribute("totalPages", pageBooks.getTotalPages());
        model.addAttribute("pageSize", size);

        if (searchQuery != null) model.addAttribute("searchQuery", searchQuery);
        if (statusName != null) model.addAttribute("statusName", statusName);
        if (categoryName != null) model.addAttribute("categoryName", categoryName);
        return "book/allBooks";
    }

    @GetMapping("/{id}/bookInfo")
    public String getBooksByBookInformation(@PathVariable UUID id,
                                            Model model,
                                            @RequestParam(required = false) String searchQuery,
                                            @RequestParam(required = false) String statusName,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "3") int size
    ) {
        var pageBooks = bookService.getBooksByBookInformation(id, searchQuery, statusName, page, size);
        var books = pageBooks.getContent();

        // Get count using max page
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE);
        var count = bookRepository.findBooksByBookInfoId(id, paging).stream().count();

        var bookInformation = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book info not found"));

        var statusOptions = BookStatus.values();

        List<Category> categoryOptions = categoryRepository.findAll();

        model.addAttribute("books", books);
        model.addAttribute("count", count);

        if (bookInformation != null) model.addAttribute("bookInformation", bookInformation);

        model.addAttribute("statusOptions", statusOptions);
        model.addAttribute("categoryOptions", categoryOptions);

        model.addAttribute("currentPage", pageBooks.getNumber() + 1);
        model.addAttribute("totalItems", pageBooks.getTotalElements());
        model.addAttribute("totalPages", pageBooks.getTotalPages());
        model.addAttribute("pageSize", size);

        if (searchQuery != null) model.addAttribute("searchQuery", searchQuery);
        if (statusName != null) model.addAttribute("statusName", statusName);
        return "book/allBooks";
    }

    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable UUID id, Model model) {
        Book book = bookService.getBook(id);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(book.getDateOfPublishing());

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
        if(!file.isEmpty()) {
            File fileDb = fileService.store(file);

            // Add fileId to bookDto
            bookDto.setFileId(fileDb.getId());
        }
        Book savedBook = bookService.createBook(bookDto);
        return "redirect:/books";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        if(book.getFile() != null) {
            fileService.deleteFile(book.getFile().getId());
        }
        bookService.deleteBookById(id);
        redirectAttributes.addFlashAttribute("message", "The book with id=" + id + " has been deleted successfully!");

        return "redirect:/books";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/file/delete/{bookId}")
    public String deleteFile(@PathVariable UUID bookId, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.getFile() != null) {
            fileService.deleteFile(book.getFile().getId());

            book.setFile(null);
            bookRepository.save(book);

            redirectAttributes.addFlashAttribute("message", "The file has been deleted successfully!");
            return "redirect:/books/edit/" + bookId;
        }
        return "redirect:/books/edit/" + bookId;
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

        // Get current file
        if(book.getFile() != null) {
            File curFile = fileService.getFile(book.getFile().getId());
            model.addAttribute("curFile", curFile);
        }

        model.addAttribute("bookInfoOptions", bookInfos);
        model.addAttribute("bookStatusOptions", bookStatuses);
        model.addAttribute("book", book);
        model.addAttribute("dateOfPublishing", formattedDate);
        return "book/editBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute BookDto bookDto, @RequestParam("file") MultipartFile file) throws IOException{
        if(!file.isEmpty()) {
            File fileDb = fileService.store(file);

            // Add fileId to bookDto
            bookDto.setFileId(fileDb.getId());

            bookService.editBook(bookDto);

            return "redirect:/books";
        } else {
            // If there is file in database delete it because user is editing with no uploaded file image
            Book book = bookService.getBook(bookDto.getId());

            if(book.getFile() != null) {
                bookDto.setFileId(book.getFile().getId());
            }
            bookService.editBook(bookDto);

            return "redirect:/books";
        }
    }
}
