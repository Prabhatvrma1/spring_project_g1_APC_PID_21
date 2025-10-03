package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    AuthService authService;
    
    @Autowired
    SessionService sessionService;
    
    // Make new user account
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Save new user
            authService.signup(user);
            
            result.put("success", true);
            result.put("message", "Account created successfully");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        
        return result;
    }
    
    // User login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // Get username and password from form
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        // Check if both fields are filled
        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            result.put("success", false);
            result.put("message", "Please enter username and password");
            return result;
        }
        
        try {
            // Check if user exists and password is correct
            User user = authService.login(username, password);
            
            // Remember user is logged in
            sessionService.createSession(session, user);
            
            result.put("success", true);
            result.put("message", "Login successful");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        
        return result;
    }
    
    // User logout
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // Clear user session
        sessionService.invalidateSession(session);
        
        result.put("success", true);
        result.put("message", "Logout successful");
        return result;
    }
    
    // Check if user is logged in
    @GetMapping("/status")
    public Map<String, Object> checkLoginStatus(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // Get current logged in user
        User user = sessionService.getCurrentUser(session);
        
        if (user != null) {
            // User is logged in - send user info
            result.put("authenticated", true);
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            userInfo.put("fullName", user.getFullName());
            result.put("user", userInfo);
        } else {
            // User is not logged in
            result.put("authenticated", false);
        }
        
        return result;
    }
}
