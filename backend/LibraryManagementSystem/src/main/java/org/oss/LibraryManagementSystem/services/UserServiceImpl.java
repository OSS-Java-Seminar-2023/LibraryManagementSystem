package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.mapper.UserMapper;
import org.oss.LibraryManagementSystem.models.Role;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.utils.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    @Override
    public List<String> getUserRoles(User user) {
        List<String> currentUserRoles = new ArrayList<>();

        user.getRoles().stream().forEach(role -> {
            currentUserRoles.add(role.getName());
        });

        return currentUserRoles;
    }

    @Override
    public List<String> formatUserDates(List<User> users) {
        List<String> formatedDates = new ArrayList<>();

        users.stream().forEach(user -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = dateFormat.format(user.getDateOfBirth());
            formatedDates.add(formattedDate);
        });

        return formatedDates;
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

        // Get default user role if no roles are defined
        Set<Role> roles = new HashSet<>();
        if(request.getRole() == null) {
            Role defaultRole = roleRepository.getDefaultRole();
            roles.add(defaultRole);
        } else {
            Role foundRole = roleRepository.findRoleByName(request.getRole());
            roles.add(foundRole);
        }
        // Add role of user as a default
        userEntity.setRoles(roles);

        // Send email
        emailService.sendEmail(request.getEmail(), "Library Management System - User account creation", "Thank you for registering account in our Library Management System.");

        return userRepository.save(userEntity);
    }

    @Override
    public User getCurrentUserDetails() {
        var user = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(user.getName()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Page<User> getAllUsers(List<String> currentUserRoles, String searchQuery, String roleName, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        Role role;

        if (searchQuery != null && roleName != null && !roleName.equals("All roles")) {
            if(currentUserRoles.get(0).equals("ADMIN")) {
                role = roleRepository.findRoleByName(roleName);
            } else {
                role = roleRepository.findRoleByName("MEMBER");
            }

            return userRepository.findByRolesEqualsAndEmailContainingIgnoreCase(role, searchQuery, paging);

        } else if (roleName != null && !roleName.equals("All roles")) {
            if(currentUserRoles.get(0).equals("ADMIN")) {
                role = roleRepository.findRoleByName(roleName);
            } else {
                role = roleRepository.findRoleByName("MEMBER");
            }
            return userRepository.findAllByRoles(role, paging);
        } else if (searchQuery != null) {
            return userRepository.findByEmailContainingOrFirstNameContainingOrLastNameContainingAllIgnoreCase(searchQuery, searchQuery, searchQuery, paging);
        } else {
            return userRepository.findAll(paging);
        }
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public String deleteUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);

        return "User with id " + id + " has been deleted.";
    }

    @Override
    public User editUser(UserDto userDto) throws ParseException {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        User editedUser = UserMapper.mapDtoToEntity(userDto);

        var roleUser = roleRepository.findRoleByName(userDto.getRole());
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        editedUser.setRoles(roles);

        editedUser.setId(user.getId()); // Preserve current entity id
        editedUser.setEnabled(user.isEnabled()); // Preserve current entity enabled status

        return userRepository.save(editedUser);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}
