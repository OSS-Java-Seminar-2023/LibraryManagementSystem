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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private final int MAX_LOANS = 3;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllLoansPage(Model model,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(defaultValue = "id") String sortField,
                                  @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        var pageLoans = loanService.getAllLoans(page, size, sortField, sortDirection);
        var loans = pageLoans.getContent();

        // Format dates
        List<String> issuedDates = loanService.formatIssudedDated(loans);
        List<String> returnedDates = loanService.formatReturnDates(loans);

        var count = loanService.getLoanCount();

        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "all");

        model.addAttribute("count", count);

        model.addAttribute("issuedDates", issuedDates);
        model.addAttribute("returnedDates", returnedDates);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('MEMBER')")
    @GetMapping("/myLoans")
    public String myLoansPage(Model model,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "id") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        // Get current user id
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User userData = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        // Get all users loans
        var pageLoans = loanService.getMyLoans(userData.getId(), page, size, sortField, sortDirection);
        var loans = pageLoans.getContent();

        // Format dates
        List<String> issuedDates = loanService.formatIssudedDated(loans);
        List<String> returnedDates = loanService.formatReturnDates(loans);

        // Get count using max page
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE);
        var count = loanRepository.findByMemberId(userData.getId(), paging).stream().count();

        model.addAttribute("member", userData);
        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "myLoans");

        model.addAttribute("count", count);

        model.addAttribute("issuedDates", issuedDates);
        model.addAttribute("returnedDates", returnedDates);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "loan/allLoans";

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{memberId}/current")
    public String getCurrentLoansPage(@PathVariable UUID memberId, Model model,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestParam(defaultValue = "id") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        var userData = userRepository.findById(memberId).orElseThrow(() -> new RuntimeException("User not found"));
        var pageLoans = loanService.getCurrentLoans(memberId, page, size, sortField, sortDirection);
        var loans = pageLoans.getContent();

        // Format dates
        List<String> issuedDates = loanService.formatIssudedDated(loans);
        List<String> returnedDates = loanService.formatReturnDates(loans);

        // Get count using max page
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE);
        var count = loanRepository.findByMemberIdAndDateReturnedIsNull(memberId, paging).stream().count();

        model.addAttribute("member", userData);
        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "current");

        model.addAttribute("count", count);

        model.addAttribute("issuedDates", issuedDates);
        model.addAttribute("returnedDates", returnedDates);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{memberId}/previous")
    public String getPreviousLoansPage(@PathVariable UUID memberId, Model model,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(defaultValue = "id") String sortField,
                                       @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        var userData = userRepository.findById(memberId).orElseThrow(() -> new RuntimeException("User not found"));
        var pageLoans = loanService.getPreviousLoans(memberId, page, size, sortField, sortDirection);
        var loans = pageLoans.getContent();

        // Format dates
        List<String> issuedDates = loanService.formatIssudedDated(loans);
        List<String> returnedDates = loanService.formatReturnDates(loans);

        // Get count using max page
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE);
        var count = loanRepository.findByMemberIdAndDateReturnedIsNotNull(memberId, paging).stream().count();

        model.addAttribute("member", userData);
        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "previous");

        model.addAttribute("count", count);

        model.addAttribute("issuedDates", issuedDates);
        model.addAttribute("returnedDates", returnedDates);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/book/{bookId}")
    public String getLoansOfBookPage(@PathVariable UUID bookId, Model model,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "id") String sortField,
                                     @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        var pageLoans = loanService.getLoansOfBook(bookId, page, size, sortField, sortDirection);
        var loans = pageLoans.getContent();
        var book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        // Format dates
        List<String> issuedDates = loanService.formatIssudedDated(loans);
        List<String> returnedDates = loanService.formatReturnDates(loans);

        // Get count using max page
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE);
        var count = loanRepository.findByBookId(bookId, paging).stream().count();

        model.addAttribute("loans", loans);
        model.addAttribute("book", book);
        model.addAttribute("loanType", "bookLoans");

        model.addAttribute("count", count);

        model.addAttribute("issuedDates", issuedDates);
        model.addAttribute("returnedDates", returnedDates);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
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
    public String startLoan (@PathVariable UUID bookId, @ModelAttribute("loanPayload") LoanDto loanDto, RedirectAttributes redirectAttributes) {
        // Check if member has more than max loans
        var numberOfMembersCurrentActiveLoans = loanRepository.countByMemberIdAndDateReturnedIsNull(loanDto.getMemberId());

        if(numberOfMembersCurrentActiveLoans >= MAX_LOANS) {
            redirectAttributes.addFlashAttribute("message", "Max loans reached for Member. Max active loans at one time is " + MAX_LOANS + " loans.");
            return "redirect:/books";
        } else {
            // Save loan to database
            var loan = loanService.createLoan(bookId, loanDto);

            // Send email to user
            emailService.sendEmail(loan.getMember().getEmail(), "Library Management System - Loan started", "<h1>Loan started</h1><p>Loan for book " + "<b>" + loan.getBook().getBookInfo().getTitle() + "</b>" +  " has been started on your name on date <b>" + loan.getDateIssued() + "</b>. " + "</p>");

            return "redirect:/loans";
        }
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
