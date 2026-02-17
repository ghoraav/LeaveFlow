package com.leaveflow.service;

import com.leaveflow.model.User;
import java.util.Optional;

public interface UserService {
    /**
     * Validates user credentials.
     * Returns the User object if login is successful, otherwise an empty Optional.
     * @param username
     * @param password
     * @return 
     */
    Optional<User> login(String username, String password);
}