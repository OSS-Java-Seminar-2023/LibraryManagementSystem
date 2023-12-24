package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.oss.LibraryManagementSystem.models.enums.BookStatus;

import java.sql.Timestamp;
import java.util.Stack;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "publisher_name")
    @NotNull(message = "publisher_name shouldn't be null")
    private String publisherName;

    @Column(name = "year_of_publishing")
    @NotNull(message = "year_of_publishing shouldn't be null")
    private Timestamp yearOfPublishing;

    @Column(name = "isbn")
    @NotNull(message = "isbn shouldn't be null")
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;

    @Column(name = "available")
    @NotNull(message = "available shouldn't be null")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "book_Info_id", referencedColumnName = "id", nullable = false)
    private BookInfo bookInfo;
}
