package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String contactNumber;
    private Date dateOfBirth;
    private String role;
}
