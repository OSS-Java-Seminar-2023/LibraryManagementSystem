package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.models.Loan;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.services.EmailService;
import org.oss.LibraryManagementSystem.services.LoanService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllLoansPage(Model model) {
        var loans = loanService.getAllLoans();

        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "all");
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('MEMBER')")
    @GetMapping("/myLoans")
    public String myLoansPage(Model model) {
        // Get current user id
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User userData = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        // Get all users loans
        List<Loan> loans = loanService.getMyLoans(userData.getId());

        model.addAttribute("member", userData);
        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "myLoans");
        return "loan/allLoans";

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
        var loans = loanService.getLoansOfBook(bookId);
        var book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        model.addAttribute("loans", loans);
        model.addAttribute("book", book);
        model.addAttribute("loanType", "bookLoans");

        return "loan/allLoans";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{bookId}/start")
    public String addNewLoanPage(@PathVariable UUID bookId, Model model, LoanDto loanDto) {
        // Get current user id as librarian
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var librarian = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        // Get member role
        var roleMember = roleRepository.getDefaultRole();

        // Get all users with member roles
        var members = userRepository.findAllByRoles(roleMember);

        // Get book by id
        var book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        model.addAttribute("librarian", librarian);
        model.addAttribute("memberOptions", members);
        model.addAttribute("loanDto", loanDto);
        model.addAttribute("book", book);

        return "loan/addNewLoan";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/{bookId}/saveLoan")
    public String startLoan (@PathVariable UUID bookId, @ModelAttribute("loanPayload") LoanDto loanDto) {
        // Save loan to database
        var loan = loanService.createLoan(bookId, loanDto);

        // Send email to user
        emailService.sendEmail(loan.getMember().getEmail(), "Library Management System - Loan started", "<h1>Loan started</h1><p>Loan for book " + "<b>" + loan.getBook().getBookInfo().getTitle() + "</b>" +  " has been started on your name on date <b>" + loan.getDateIssued() + "</b>. " + "</p>");

        return "redirect:/loans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{loanId}/end")
    public String endLoan(@PathVariable UUID loanId, Model model) {
        // End loan
        var loan = loanService.endLoan(loanId);

        // Send email to user
        emailService.sendEmail(loan.getMember().getEmail(), "Library Management System - Loan ended", "<h1>Loan ended</h1><p>Loan for book " + "<b>" + loan.getBook().getBookInfo().getTitle() + "</b>" + " has been ended on your name on date <b>" + loan.getDateReturned() + "</b>. " + "</p>");

        return "redirect:/loans";
    }


}