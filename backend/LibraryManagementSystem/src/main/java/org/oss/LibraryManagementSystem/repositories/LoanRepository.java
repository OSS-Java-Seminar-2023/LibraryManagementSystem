package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findAll();
    List<Loan> findByBookId(UUID bookId);

    List<Loan> findByMemberId(UUID memberId);

    List<Loan> findByMemberIdAndDateReturnedIsNull(UUID memberId);

    List<Loan> findByMemberIdAndDateReturnedIsNotNull(UUID memberId);

    int countByMemberIdAndDateReturnedIsNotNull(UUID memberId);
}
