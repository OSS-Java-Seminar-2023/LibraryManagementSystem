package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.services.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping
    public String getAllBooksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        Long count = bookService.getBookCount();

        model.addAttribute("books", books);
        model.addAttribute("count", count);
        return "book/allBooks";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewBookPage(Model model, BookDto bookDto) {
        model.addAttribute("bookDto", bookDto);
        return "book/addNewBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute BookDto bookDto) {
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

        model.addAttribute("book", book);
        return "book/editBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/updateBook")
    public String updateBook(@ModelAttribute BookDto bookDto) {
        Book book = bookService.editBook(bookDto);

        return "redirect:/books";
    }
}
