package com.leaveflow.service;

import com.leaveflow.model.User;
import com.leaveflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> login(String username, String password) {
        // Find the user by their username
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Check if user exists AND the password matches
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // IMPORTANT: This is plain-text password checking.
            // In a real app, you would use a password encoder (like Spring Security's BCrypt).
            if (user.getPassword().equals(password)) {
                return userOptional;
            }
        }

        // If user not found or password incorrect, return empty
        return Optional.empty();
    }
}