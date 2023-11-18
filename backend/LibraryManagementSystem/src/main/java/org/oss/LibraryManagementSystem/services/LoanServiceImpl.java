package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }
}
