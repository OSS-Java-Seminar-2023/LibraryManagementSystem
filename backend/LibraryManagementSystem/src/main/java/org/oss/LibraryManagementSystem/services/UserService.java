package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    boolean areInputsInvalid(UserDto request);
    User registerUser(UserDto request);

    User getCurrentUserDetails();

    List<User> getAllUsers();
    User getUserById(UUID id);

    String deleteUserById(UUID id);

    User editUser(UserDto userDto) throws ParseException;
}
