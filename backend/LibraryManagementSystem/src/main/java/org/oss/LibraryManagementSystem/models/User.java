package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
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
    private Timestamp dateOfBirth;

    @ManyToOne
    @JoinColumn(name="user_role", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

    @Column(name = "enabled")
    private boolean enabled;
}
