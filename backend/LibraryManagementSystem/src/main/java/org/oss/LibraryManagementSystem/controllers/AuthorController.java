package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.AuthorDto;
import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.oss.LibraryManagementSystem.services.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllAuthorsPage(Model model,
                                    @RequestParam(required = false) String searchQuery,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @RequestParam(defaultValue = "id") String sortField,
                                    @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        var pageAuthors = authorService.getAllAuthors(searchQuery, page, size, sortField, sortDirection);
        var authors = pageAuthors.getContent();

        Long count = authorService.getAuthorCount();

        model.addAttribute("authors", authors);
        model.addAttribute("count", count);

        model.addAttribute("currentPage", pageAuthors.getNumber() + 1);
        model.addAttribute("totalItems", pageAuthors.getTotalElements());
        model.addAttribute("totalPages", pageAuthors.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        if (searchQuery != null) model.addAttribute("searchQuery", searchQuery);

        return "author/allAuthors";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewAuthorPage(Model model, AuthorDto authorDto) {
        model.addAttribute("authorDto", authorDto);
        return "author/addNewAuthor";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveAuthor")
    public String saveAuthor(@ModelAttribute AuthorDto authorDto) {
        Author savedAuthor = authorService.createAuthor(authorDto);
        return "redirect:/authors";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthorById(id);
            redirectAttributes.addFlashAttribute("message", "The author with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/authors";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/edit/{id}")
    public String editAuthorPage(@PathVariable UUID id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));

        model.addAttribute("author", author);
        return "author/editAuthor";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateAuthor")
    public String updateAuthor(@ModelAttribute AuthorDto authorDto) {
        Author author = authorService.editAuthor(authorDto);

        return "redirect:/authors";
    }
}
