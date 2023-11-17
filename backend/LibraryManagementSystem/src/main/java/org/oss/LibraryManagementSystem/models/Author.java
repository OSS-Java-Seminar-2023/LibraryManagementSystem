package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name")
    @NotNull(message = "first_name shouldn't be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "last_name shouldn't be null")
    private String lastName;
}
