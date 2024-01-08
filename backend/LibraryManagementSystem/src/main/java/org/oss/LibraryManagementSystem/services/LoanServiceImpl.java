package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
}
