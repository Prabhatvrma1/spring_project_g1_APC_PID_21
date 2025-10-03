package com.example.demo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Save user to database
    public User save(User user) {
        // Turn skills list into one string
        String allSkills = "";
        if (user.getSkillsList() != null && user.getSkillsList().size() > 0) {
            allSkills = String.join(",", user.getSkillsList());
        }
        
        String sql = "INSERT INTO `user` (username, email, password, full_name, phone, address, linkedin, website, summary, experience, education, certifications, projects, skills) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getPassword(), user.getFullName(),
                          user.getPhone(), user.getAddress(), user.getLinkedin(), user.getWebsite(),
                          user.getSummary(), user.getExperience(), user.getEducation(),
                          user.getCertifications(), user.getProjects(), allSkills);
        return user;
    }

    // Find user by username
    public User findByUsername(String username) {
        String sql = "SELECT * FROM `user` WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("full_name"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setLinkedin(rs.getString("linkedin"));
            user.setWebsite(rs.getString("website"));
            user.setSummary(rs.getString("summary"));
            user.setExperience(rs.getString("experience"));
            user.setEducation(rs.getString("education"));
            user.setCertifications(rs.getString("certifications"));
            user.setProjects(rs.getString("projects"));
            
            // Split skills string back into list
            String allSkills = rs.getString("skills");
            List<String> skillsList = new ArrayList<>();
            if (allSkills != null && allSkills.length() > 0) {
                String[] skillsArray = allSkills.split(",");
                for (String skill : skillsArray) {
                    String cleanSkill = skill.trim();
                    if (cleanSkill.length() > 0) {
                        skillsList.add(cleanSkill);
                    }
                }
            }
            user.setSkillsList(skillsList);
            
            return user;
        }, username);

        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    // Update user profile
    public User updateProfile(User user) {
        // Turn skills list into one string
        String allSkills = "";
        if (user.getSkillsList() != null && user.getSkillsList().size() > 0) {
            allSkills = String.join(",", user.getSkillsList());
        }
        
        String sql = "UPDATE `user` SET phone = ?, address = ?, linkedin = ?, website = ?, summary = ?, experience = ?, education = ?, certifications = ?, projects = ?, skills = ? WHERE username = ?";
        jdbcTemplate.update(sql, user.getPhone(), user.getAddress(), user.getLinkedin(), user.getWebsite(),
                          user.getSummary(), user.getExperience(), user.getEducation(),
                          user.getCertifications(), user.getProjects(), allSkills, user.getUsername());
        return user;
    }

    // Check if username already exists
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM `user` WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count > 0;
    }
}