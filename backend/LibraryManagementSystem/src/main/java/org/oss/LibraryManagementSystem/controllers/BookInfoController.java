package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.oss.LibraryManagementSystem.services.BookInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/bookInfos")
@AllArgsConstructor
public class BookInfoController {
    private final BookInfoService bookInfoService;

    private final BookInfoRepository bookInfoRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllBookInfosPage(Model model,
                                      @RequestParam(required = false) String searchQuery,
                                      @RequestParam(required = false) String categoryName,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestParam(defaultValue = "id") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        var pageBookInfos = bookInfoService.getAllBookInfos(searchQuery, categoryName, page, size, sortField, sortDirection);
        var bookInfos = pageBookInfos.getContent();

        Long count = bookInfoService.getBookInfoCount();

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("categoryOptions", categories);

        model.addAttribute("bookInfos", bookInfos);
        model.addAttribute("count", count);

        model.addAttribute("currentPage", pageBookInfos.getNumber() + 1);
        model.addAttribute("totalItems", pageBookInfos.getTotalElements());
        model.addAttribute("totalPages", pageBookInfos.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        if (searchQuery != null) model.addAttribute("searchQuery", searchQuery);

        return "bookInfo/allBookInfos";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewBookInfoPage(Model model, BookInfoDto bookInfoDto) {
        List<Author> authors = authorRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("bookInfoDto", bookInfoDto);
        model.addAttribute("authorOptions", authors);
        model.addAttribute("categoryOptions", categories);
        return "bookInfo/addNewBookInfo";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveBookInfo")
    public String saveBookInfo(@ModelAttribute BookInfoDto bookInfoDto) {
        BookInfo bookInfo = bookInfoService.createBookInfo(bookInfoDto);
        return "redirect:/bookInfos";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/delete/{id}")
    public String deleteBookInfo(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            bookInfoService.deleteBookInfoById(id);
            redirectAttributes.addFlashAttribute("message", "The Book Information with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/bookInfos";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/edit/{id}")
    public String editBookInfoPage(@PathVariable UUID id, Model model) {
        List<Author> authors = authorRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        BookInfo bookInfo = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book Information not found"));

        model.addAttribute("bookInfo", bookInfo);
        model.addAttribute("authorOptions", authors);
        model.addAttribute("categoryOptions", categories);
        return "bookInfo/editBookInfo";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateBookInfo")
    public String updateBookInfo(@ModelAttribute BookInfoDto bookInfoDto) {
        BookInfo bookInfo = bookInfoService.editBookInfo(bookInfoDto);

        return "redirect:/bookInfos";
    }
}
