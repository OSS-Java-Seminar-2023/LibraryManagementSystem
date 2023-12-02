package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name")
    @NotNull(message = "First name shouldn't be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name shouldn't be null")
    private String lastName;

    @Column(name = "username")
    @NotNull(message = "Username shouldn't be null")
    private String username;

    @Column(name = "email")
    @NotNull(message = "Email shouldn't be null")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Password shouldn't be null")
    private String password;

    @Column(name = "contact_number")
    @NotNull(message = "Contact number shouldn't be null")
    private String contactNumber;

    @Column(name = "date_of_birth")
    @NotNull(message = "Date of birth shouldn't be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "enabled")
    private boolean enabled;
}
