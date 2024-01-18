package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookInfoRepository extends JpaRepository<BookInfo, UUID> {
    Page<BookInfo> findAll(Pageable pageable);
}
