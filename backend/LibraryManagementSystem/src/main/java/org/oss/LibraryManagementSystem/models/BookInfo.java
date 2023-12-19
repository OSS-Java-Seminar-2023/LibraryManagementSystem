package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_info")
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title")
    @NotNull(message = "title shouldn't be null")
    private String title;

    @Column(name = "description")
    @NotNull(message = "description shouldn't be null")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "bookInfo_author",
            joinColumns = @JoinColumn(name = "bookInfo_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "bookInfo_category",
            joinColumns = @JoinColumn(name = "bookInfo_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}
