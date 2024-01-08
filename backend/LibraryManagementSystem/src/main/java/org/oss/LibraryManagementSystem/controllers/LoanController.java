package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.services.LoanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanRepository loanRepository;
    private final LoanService loanService;
    private final BookRepository bookRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/")
    public String getAllLoansPage(Model model) {
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/myLoans")
    public String myLoansPage(Model model) {
        return "loan/myLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{userId}/current")
    public String getCurrentLoansPage(@PathVariable UUID userId, Model model) {
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{userId}/previous")
    public String getPreviousLoansPage(@PathVariable UUID userId, Model model) {
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/book/{bookId}")
    public String getLoansOfBookPage(@PathVariable UUID bookId, Model model) {
        return "loan/allLoans";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{bookId}/start")
    public String addNewLoanPage(@PathVariable UUID bookId, Model model) {
        return "loan/addNewLoan";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/{bookId}/saveLoan")
    public String saveLoan(@PathVariable UUID bookId) {
        return "redirect:/loans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{bookId}/end")
    public String endLoan(@PathVariable UUID bookId, Model model) {
        return "redirect:/loans";
    }


}
