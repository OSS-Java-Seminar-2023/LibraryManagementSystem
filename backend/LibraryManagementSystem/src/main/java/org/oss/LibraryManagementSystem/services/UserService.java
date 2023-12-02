package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    boolean areInputsInvalid(UserDto request);
    User registerUser(UserDto request);
}
