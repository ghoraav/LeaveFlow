package com.leaveflow.controller;

import com.leaveflow.model.User;
import com.leaveflow.service.UserService;
import jakarta.servlet.http.HttpSession; // Note: using jakarta.servlet
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Shows the login page
    @GetMapping({"/", "/login"})
    public String showLoginPage(HttpSession session) {
        // If user is already logged in, redirect to dashboard
        if (session.getAttribute("user") != null) {
            return "redirect:dashboard";
        }
        return "login"; // This will resolve to /WEB-INF/views/login.jsp
    }

    // Processes the login form
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        Optional<User> userOptional = userService.login(username, password);

        if (userOptional.isPresent()) {
            // Login successful!
            // Store the entire User object in the session
            session.setAttribute("user", userOptional.get());
            return "redirect:dashboard";
        } else {
            // Login failed
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Go back to login.jsp with an error message
        }
    }

    // Handles logout
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        // Invalidate the session (remove all attributes, including the user)
        session.invalidate();
        return "redirect:login";
    }
}