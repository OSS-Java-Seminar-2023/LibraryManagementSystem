package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Page<Book> findAll(Pageable pageable);
    Page<Book> findBooksByBookInfoId(UUID id, Pageable pageable);

    List<Book> findBooksByBookInfoId(UUID id);
}
