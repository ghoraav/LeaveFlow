package com.leaveflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity marks this as a class that Hibernate should manage
@Entity
// @Table links this class to the 'users' table in your database
@Table(name = "users")
public class User {

    // @Id marks this field as the primary key
    @Id
    // @GeneratedValue tells Hibernate it's an auto-incrementing column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @Column links this field to the 'username' column
    // unique=true and nullable=false add database-level constraints
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    // ---
    // Getters and Setters
    // Hibernate needs these to set and get data.
    // ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}