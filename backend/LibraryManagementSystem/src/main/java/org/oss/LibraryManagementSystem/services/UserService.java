package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService {
    boolean areInputsInvalid(UserDto request);
    User registerUser(UserDto request);

    User getCurrentUserDetails();

    User getUserById(UUID id);
}
