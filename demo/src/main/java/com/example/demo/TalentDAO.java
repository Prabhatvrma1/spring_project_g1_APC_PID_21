package com.example.demo;

// template to talk to database easily
// auto wired gives things automatically
// repository marks as ki yae database kae sath kaa kar rha hae
//jdbc template - tool to sql sae baat karne kae liye 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TalentDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Save a talent to database
    public Talent save(Talent talent) {
        String sql = "INSERT INTO talent (name, email, skills) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, talent.getName(), talent.getEmail(), talent.getSkills());
        return talent;
    }
    
    // Get all talents from database
    public List<Talent> findAll() {
        String sql = "SELECT * FROM talent";
        // for each talen you find do thisss...{ ------}
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Talent talent = new Talent();
            talent.setId(rs.getLong("id"));
            talent.setName(rs.getString("name"));
            talent.setEmail(rs.getString("email"));
            talent.setSkills(rs.getString("skills"));
            return talent;
        });
    }
    
    // Find a talent by ID
    public Talent findById(Long id) {
        String sql = "SELECT * FROM talent WHERE id = ?";
        List<Talent> talents = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Talent talent = new Talent();
            talent.setId(rs.getLong("id"));
            talent.setName(rs.getString("name"));
            talent.setEmail(rs.getString("email"));
            talent.setSkills(rs.getString("skills"));
            return talent;
        }, id);
        
        if (talents.isEmpty()) {
            return null;
        }
        return talents.get(0);
    }
    
    // Update a talent in database
    public Talent update(Long id, Talent talent) {
        String sql = "UPDATE talent SET name = ?, email = ?, skills = ? WHERE id = ?";
        jdbcTemplate.update(sql, talent.getName(), talent.getEmail(), talent.getSkills(), id);
        talent.setId(id);
        return talent;
    }
    
    // Delete a talent from database
    public void deleteById(Long id) {
        String sql = "DELETE FROM talent WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    // Check if talent exists
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM talent WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}