package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String contactNumber;
    private Date dateOfBirth;
}
