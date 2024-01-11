package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByBookId(UUID bookId);

    List<Loan> findByMemberId(UUID memberId);
}
