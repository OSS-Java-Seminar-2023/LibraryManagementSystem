package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.models.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LoanService {

    List<String> formatIssudedDated(List<Loan> loans);

    List<String> formatReturnDates(List<Loan> loans);

    Page<Loan> getAllLoans(int page, int size, String sortField, String sortDirection);

    Page<Loan> getMyLoans(UUID userId, int page, int size, String sortField, String sortDirection);

    Page<Loan> getCurrentLoans(UUID userId, int page, int size, String sortField, String sortDirection);

    Page<Loan> getPreviousLoans(UUID userId, int page, int size, String sortField, String sortDirection);

    Page<Loan> getLoansOfBook(UUID bookId, int page, int size, String sortField, String sortDirection);

    Loan createLoan(UUID bookId, LoanDto loanDto);

    Loan endLoan(UUID loanId);

    Long getLoanCount();
}
