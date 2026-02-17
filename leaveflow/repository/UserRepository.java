package com.leaveflow.repository;

import com.leaveflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// We extend JpaRepository, telling it to manage the 'User' entity,
// which has a primary key of type 'Integer'.
public interface UserRepository extends JpaRepository<User, Integer> {

    // Spring Data JPA automatically understands this method name.
    // It will generate the SQL query: "SELECT * FROM users WHERE username = ?"
    Optional<User> findByUsername(String username);
    
}