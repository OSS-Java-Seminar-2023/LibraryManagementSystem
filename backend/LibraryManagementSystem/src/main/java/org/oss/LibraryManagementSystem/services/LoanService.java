package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.models.Loan;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    List<Loan> getAllLoans();

    List<Loan> getMyLoans(UUID userId);

    List<Loan> getCurrentLoans(UUID userId);

    List<Loan> getPreviousLoans(UUID userId);

    List<Loan> getLoansOfBook(UUID bookId);

    Loan createLoan(UUID bookId, LoanDto loanDto);

    Loan endLoan(UUID id);
}
