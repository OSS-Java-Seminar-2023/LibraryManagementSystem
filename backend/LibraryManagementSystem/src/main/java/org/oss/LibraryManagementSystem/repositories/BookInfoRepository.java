package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookInfoRepository extends JpaRepository<BookInfo, UUID> {
}
