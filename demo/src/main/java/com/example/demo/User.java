package com.example.demo;

import java.util.List;

public class User {

    Long id;
    String username;
    String email;
    String password;
    String fullName;
    String phone;
    String address;
    String linkedin;
    String website;
    String summary;
    String experience;
    String education;
    String certifications;
    String projects;
    List<String> skillsList;

    public User() {}

    // Make user with basic info
    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Make user with ID from database
    public User(Long id, String username, String email, String password, String fullName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Get methods
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getLinkedin() { return linkedin; }
    public String getWebsite() { return website; }
    public String getSummary() { return summary; }
    public String getExperience() { return experience; }
    public String getEducation() { return education; }
    public String getCertifications() { return certifications; }
    public String getProjects() { return projects; }
    public List<String> getSkillsList() { return skillsList; }

    // Set methods
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }
    public void setWebsite(String website) { this.website = website; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setExperience(String experience) { this.experience = experience; }
    public void setEducation(String education) { this.education = education; }
    public void setCertifications(String certifications) { this.certifications = certifications; }
    public void setProjects(String projects) { this.projects = projects; }
    public void setSkillsList(List<String> skillsList) { this.skillsList = skillsList; }
}
