package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("users/login")
    public String getLoginPage() {
        return "user/login";
    }

    @GetMapping("users/register")
    public String getRegisterPage() {
        return "user/register";
    }

    @PostMapping("users/register")
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
}
