package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.dto.CategoryDto;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.BookInfoRepository;
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllBookInfosPage(Model model) {
        List<BookInfo> bookInfos = bookInfoService.getAllBookInfos();
        Long count = bookInfoService.getBookInfoCount();

        model.addAttribute("bookInfos", bookInfos);
        model.addAttribute("count", count);
        return "bookInfo/allBookInfos";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewBookInfoPage(Model model, BookInfoDto bookInfoDto) {
        model.addAttribute("bookInfoDto", bookInfoDto);
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
        BookInfo bookInfo = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book Information not found"));

        model.addAttribute("bookInfo", bookInfo);
        return "bookInfo/editBookInfo";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateBookInfo")
    public String updateBookInfo(@ModelAttribute BookInfoDto bookInfoDto) {
        BookInfo bookInfo = bookInfoService.editBookInfo(bookInfoDto);

        return "redirect:/bookInfos";
    }
}
