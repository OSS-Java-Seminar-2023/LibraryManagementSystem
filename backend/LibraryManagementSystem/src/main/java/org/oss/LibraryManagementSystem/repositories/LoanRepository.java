package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    Page<Loan> findAll(Pageable pageable);
    Page<Loan> findByBookId(UUID bookId, Pageable pageable);

    Page<Loan> findByMemberId(UUID memberId, Pageable pageable);

    Page<Loan> findByMemberIdAndDateReturnedIsNull(UUID memberId, Pageable pageable);

    Page<Loan> findByMemberIdAndDateReturnedIsNotNull(UUID memberId, Pageable pageable);

    int countByMemberIdAndDateReturnedIsNull(UUID memberId);
}
