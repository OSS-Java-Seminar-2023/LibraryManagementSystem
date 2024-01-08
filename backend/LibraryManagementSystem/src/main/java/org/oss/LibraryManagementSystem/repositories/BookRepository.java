package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findBooksByBookInfoId(UUID id);
}
