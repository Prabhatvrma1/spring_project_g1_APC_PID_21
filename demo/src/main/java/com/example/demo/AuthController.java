package com.example.demo;

//responseentity  wraps your response before sending it to website
//crossorigin allows requests from different origins ( different api sae)




import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
//give me httpsection to manage user sessions


// This controller handles all user authentication web requests
// It receives JSON from frontend and returns JSON responses


//rest controller  web requests handle karti hae

@RestController


@RequestMapping("/api/auth")


//crossorigin means allow this to be accessed from different origins
@CrossOrigin(origins = "*")
public class AuthController {
    
    // Connect to business logic services
    @Autowired
    private AuthService authService;
    
    @Autowired
    private SessionService sessionService;
    
    // CREATE NEW USER ACCOUNT - POST /api/auth/signup


    @PostMapping("/signup")
    //post mapping  http post requests handle karti hae

    public ResponseEntity<Map<String, Object>> signup(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Call business logic to save user
            authService.signup(user);
            
            // Send success response
            response.put("success", true);
            response.put("message", "Account created successfully");
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            // Send error response  
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // USER LOGIN - POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Get username and password from frontend
            String username = loginData.get("username");
            String password = loginData.get("password");
            
            // Check if both fields are provided
            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Please enter username and password");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Try to authenticate user
            User user = authService.login(username, password);
            
            // Remember user is logged in
            sessionService.createSession(session, user);
            
            // Send success response
            response.put("success", true);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            // Send login failed response
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // USER LOGOUT - POST /api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // Clear user session
        sessionService.invalidateSession(session);
        
        response.put("success", true);
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }
    
    // CHECK IF USER IS LOGGED IN - GET /api/auth/status
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkLoginStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        // Get current logged in user
        User currentUser = sessionService.getCurrentUser(session);
        
        if (currentUser != null) {
            // User is logged in - send user info
            response.put("authenticated", true);
            response.put("user", Map.of(
                "id", currentUser.getId(),
                "username", currentUser.getUsername(),
                "email", currentUser.getEmail(),
                "fullName", currentUser.getFullName()
            ));
        } else {
            // User is not logged in
            response.put("authenticated", false);
        }
        
        return ResponseEntity.ok(response);
    }
}
