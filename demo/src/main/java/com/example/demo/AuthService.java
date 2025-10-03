package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserDAO userDAO;
    
    // SIMPLE SIGNUP - Just check username and save user
    public User signup(User user) {
        // Only check if username already exists (removed email check as requested)
        if (userDAO.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        // Save user to database
        return userDAO.save(user);
    }
    
    // SIMPLE LOGIN - Check username and password
    public User login(String username, String password) {
        // Find user by username
        User user = userDAO.findByUsername(username);
        
        // If user not found
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        // Check password (simple text comparison)
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        
        return user;
    }
}
