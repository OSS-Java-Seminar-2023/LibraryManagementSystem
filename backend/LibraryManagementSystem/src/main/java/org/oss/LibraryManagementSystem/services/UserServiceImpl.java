package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.mapper.UserMapper;
import org.oss.LibraryManagementSystem.models.Role;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.utils.Validator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public boolean areInputsInvalid(UserDto request) {
        return Validator.isStringNullOrEmpty(request.getFirstName()) ||
                Validator.isStringNullOrEmpty(request.getLastName()) ||
                Validator.isStringNullOrEmpty(request.getUsername()) ||
                Validator.isStringNullOrEmpty(request.getEmail()) ||
                Validator.isStringNullOrEmpty(request.getPassword()) ||
                Validator.isStringNullOrEmpty(request.getContactNumber());
    }

    @Override
    public User registerUser(UserDto request) {
        if(areInputsInvalid(request)) {
            throw new RuntimeException("Invalid user input");
        }

        // Is email taken
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with that email already exists");
        }

        // Is username taken
        if(userRepository.getUserByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User with that username already exists");
        }

        User userEntity = UserMapper.mapDtoToEntity(request);
        // Enable user account by default
        userEntity.setEnabled(true);

        // Add role of user as a default

        // Send email
        emailService.sendEmail(request.getEmail(), "Library Management System - User account creation", "Thank you for registering account in our Library Management System.");

        return userRepository.save(userEntity);
    }

    @Override
    public User getCurrentUserDetails() {
        var user = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(user.getName());
        return userRepository.findByEmail(user.getName()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
