package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.models.Loan;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
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
        return null;
    }

    @Override
    public Loan createLoan(UUID bookId, LoanDto loanDto) {
        return null;
    }

    @Override
    public Loan endLoan(UUID id) {
        return null;
    }
}
