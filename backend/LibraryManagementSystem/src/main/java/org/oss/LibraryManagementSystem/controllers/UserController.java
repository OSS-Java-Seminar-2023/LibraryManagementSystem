package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto request, @RequestParam String confirmPassword, Model model) {
        try {
            if (!request.getPassword().equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }
            User registeredUser = userService.registerUser(request);
            return registeredUser == null ? "user/register_error_page" : "user/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "user/register";
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String allUsersPage(Authentication authentication, Model model) {
        List<User> users = userService.getAllUsers();

        var currentUser = userRepository.findByEmail(authentication.getName());
        var currentUserRoles = new ArrayList<String>();
        for (var role : currentUser.get().getRoles()) {
            currentUserRoles.add(role.getName());
        }

        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserRole", currentUserRoles.get(0));
        return "user/allUsers";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{id}")
    public String userDetailsPage(Model model, @PathVariable UUID id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/userDetails";
    }

    @GetMapping("/myDetails")
    public String myDetailsPage(Model model) {
        User user = userService.getCurrentUserDetails();
        model.addAttribute("user", user);
        return "user/userDetails";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewUserPage(Model model, UserDto userDto) {
        var roles = roleRepository.findAll();

        model.addAttribute("userDto", userDto);
        model.addAttribute("roleOptions", roles);

        return "user/addNewUser";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDto userDto) {
        User registeredUser = userService.registerUser(userDto);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/edit/{id}")
    public String editUserPage(@PathVariable UUID id, Model model) {
        var roles = roleRepository.findAll();
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(user.getDateOfBirth());

        model.addAttribute("dateOfBirth", formattedDate);
        model.addAttribute("userRequest", user);
        model.addAttribute("roleOptions", roles);

        System.out.println(formattedDate);

        return "user/editUser";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute UserDto userRequest) throws ParseException {
        System.out.println(userRequest);
        User editedUser = userService.editUser(userRequest);

        return "redirect:/users";
    }

}
