package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Loan;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final int MAX_LOANS = 3;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getMyLoans(UUID userId) {
        return loanRepository.findByMemberId(userId);
    }

    @Override
    public List<Loan> getCurrentLoans(UUID userId) {
        return null;
    }

    @Override
    public List<Loan> getPreviousLoans(UUID userId) {
        return null;
    }

    @Override
    public List<Loan> getLoansOfBook(UUID bookId) {
        return loanRepository.findByBookId(bookId);
    }

    @Override
    public Loan createLoan(UUID bookId, LoanDto loanDto) {
        System.out.println(loanDto);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        // Check if books is available
        // Get count of current loans of user

        if(book != null && book.isAvailable() && book.getBookStatus().name().equals("OK")) {
            Loan loan = new Loan();
            loan.setBook(book);
            // Get current user id as librarian
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var librarian = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

            loan.setMember(userRepository.findById(loanDto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found")));
            loan.setLibrarian(librarian);

            var dateIssued = new Timestamp(System.currentTimeMillis());

            loan.setDateIssued(dateIssued);
            loan.setDateReturned(null);

            book.setAvailable(false);

            bookRepository.save(book);
            return loanRepository.save(loan);
        }

        return null;
    }

    @Override
    public Loan endLoan(UUID id) {
        return null;
    }
}
