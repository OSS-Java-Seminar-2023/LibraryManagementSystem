package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.mapper.LoanMapper;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Loan;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<String> formatIssudedDated(List<Loan> loans) {
        List<String> issuedDates = new ArrayList<>();
        loans.stream().forEach(loan -> {
            if(loan.getDateIssued() == null) {
                issuedDates.add("null");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String formattedDate = dateFormat.format(loan.getDateIssued());
                issuedDates.add(formattedDate);
            }
        });
        return issuedDates;
    }

    @Override
    public List<String> formatReturnDates(List<Loan> loans) {
        List<String> returnedDates = new ArrayList<>();
        loans.stream().forEach(loan -> {
            if(loan.getDateReturned() == null) {
                returnedDates.add("null");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String formattedDate = dateFormat.format(loan.getDateReturned());
                returnedDates.add(formattedDate);
            }
        });
        return returnedDates;
    }

    @Override
    public Page<Loan> getAllLoans(int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findAll(paging);
    }

    @Override
    public Page<Loan> getMyLoans(UUID userId, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findByMemberId(userId, paging);
    }

    @Override
    public Page<Loan> getCurrentLoans(UUID userId, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findByMemberIdAndDateReturnedIsNull(userId, paging);
    }

    @Override
    public Page<Loan> getPreviousLoans(UUID userId, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findByMemberIdAndDateReturnedIsNotNull(userId, paging);
    }

    @Override
    public Page<Loan> getLoansOfBook(UUID bookId, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findByBookId(bookId, paging);
    }

    @Override
    public Loan createLoan(UUID bookId, LoanDto loanDto) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        // Check if books is available
        // Get count of current loans of user
        var numberOfMembersCurrentActiveLoans = loanRepository.countByMemberIdAndDateReturnedIsNull(loanDto.getMemberId());

        if(book != null && book.isAvailable() && book.getBookStatus().name().equals("OK") && numberOfMembersCurrentActiveLoans < MAX_LOANS) {
            Loan loan = new Loan();
            loan.setBook(book);
            // Get current user id as librarian
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var librarian = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
            var member = userRepository.findById(loanDto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found"));

            loan.setMember(member);
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
    public Loan endLoan(UUID loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        var book = bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found"));

        if(loan != null && book != null && loan.getDateReturned() == null) {
            var dateReturned = new Timestamp(System.currentTimeMillis());

            loan.setDateReturned(dateReturned);

            book.setAvailable(true);
            bookRepository.save(book);

            return loanRepository.save(loan);
        }

        return null;
    }

    @Override
    public Long getLoanCount() {
        return loanRepository.count();
    }
}
