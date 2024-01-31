package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Page<Book> findAll(Pageable pageable);
    Page<Book> findBooksByBookInfoId(UUID id, Pageable pageable);

    List<Book> findBooksByBookInfoId(UUID id);

    // Books by title
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE LOWER(bi.title) LIKE LOWER(CONCAT('%',:title,'%'))", nativeQuery = true)
    Page<Book> findByBookInfoTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    // Books by title, status and category (Native query)
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
                    "inner join bookInfo bi on b.book_info_id = bi.id " +
                    "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
                    "inner join category c on bc.category_id = c.id " +
                    "WHERE LOWER(bi.title) LIKE LOWER(CONCAT('%',:title,'%')) AND " +
                    "CAST(b.book_status AS text) = :status " +
                    "AND LOWER(c.name) LIKE LOWER(CONCAT('%',:category,'%'))", nativeQuery = true)
    Page<Book> findByBookInfoTitleAndStatusAndCategory(@Param("title") String title, @Param("status") String status,@Param("category") String category, Pageable pageable);

    // Books by status and category (Native query)
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE CAST(b.book_status AS text) = :status " +
            "AND LOWER(c.name) LIKE LOWER(CONCAT('%',:category,'%'))", nativeQuery = true)
    Page<Book> findByStatusAndCategory(@Param("status") String status, @Param("category") String category, Pageable pageable);

    // Books by status (Native query)
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE CAST(b.book_status AS text) = :status ", nativeQuery = true)
    Page<Book> findByStatus(@Param("status") String status, Pageable pageable);

    // Books by category (Native query)
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:category,'%')) ", nativeQuery = true)
    Page<Book> findByBookInfoCategoryContainingIgnoreCase(@Param("category") String category, Pageable pageable);

    // Books by bookInfoId and publisher name
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE LOWER(b.publisher_name) LIKE LOWER(CONCAT('%',:publisher_name,'%')) " +
            "AND b.book_info_id = :id", nativeQuery = true)
    Page<Book> findBooksByBookInfoIdAndTitleContainingIgnoreCase(@Param("id") UUID id, @Param("publisher_name") String publisher_name, Pageable pageable);

    // Books by bookInfoId, publisher_name, status (Native query)
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE LOWER(b.publisher_name) LIKE LOWER(CONCAT('%',:publisher_name,'%')) AND " +
            "CAST(b.book_status AS text) = :status " +
            "AND b.book_info_id = :id", nativeQuery = true)
    Page<Book> findBooksByBookInfoIdAndTitleContainingIgnoreCaseAndStatusAndCategoriesContainingIgnoreCase(@Param("id") UUID id, @Param("publisher_name") String publisher_name, @Param("status") String status, Pageable pageable);

    // Books by bookInfo id and status (Native query)
    @Query(value = "SELECT \n" +
            "\tb.id\n" +
            "\t,b.publisher_name\n" +
            "\t,b.date_of_publishing\n" +
            "\t,b.isbn\n" +
            "\t,b.book_status\n" +
            "\t,b.available\n" +
            "\t,b.book_info_id\n" +
            "\t,b.image\n" +
            "\t,b.file_id\n" +
            "\t,bi.title\n" +
            "\t,bi.description\n" +
            "\t,bc.category_id\n" +
            "\t,c.name\n" +
            "FROM book b " +
            "inner join bookInfo bi on b.book_info_id = bi.id " +
            "inner join book_info_category bc on b.book_info_id = bc.book_info_id " +
            "inner join category c on bc.category_id = c.id " +
            "WHERE CAST(b.book_status AS text) = :status " +
            "AND b.book_info_id = :id", nativeQuery = true)
    Page<Book> findBooksByBookInfoIdAndStatus(@Param("id") UUID id, @Param("status") String status, Pageable pageable);

}

