package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Save a user to database
    public User save(User user) {
        String sql = "INSERT INTO user (username, email, password, full_name) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getFullName());
        return user;
    }
    
    // Find a user by username
    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("full_name"));
            return user;
        }, username);
        
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
    
    // Check if username already exists
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count > 0;
    }
}