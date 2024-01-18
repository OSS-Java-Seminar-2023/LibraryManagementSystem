package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface UserService {

    List<String> getUserRoles(User user);

    List<String> formatUserDates(List<User> users);
    boolean areInputsInvalid(UserDto request);
    User registerUser(UserDto request);

    User getCurrentUserDetails();

    Page<User> getAllUsers(List<String> currentUserRoles, String searchQuery, String roleName, int page, int size, String sortField, String sortDirection);
    User getUserById(UUID id);

    String deleteUserById(UUID id);

    User editUser(UserDto userDto) throws ParseException;

    long getUserCount();
}
