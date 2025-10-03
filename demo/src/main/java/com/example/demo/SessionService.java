package com.example.demo;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
    
    // Simple session management
    
    public void createSession(HttpSession session, User user) {
        // Store user info in session
        session.setAttribute("user", user);
    }
    
    public User getCurrentUser(HttpSession session) {
        // Get user from session
        return (User) session.getAttribute("user");
    }
    
    public void invalidateSession(HttpSession session) {
        // Clear session
        session.invalidate();
    }
}
