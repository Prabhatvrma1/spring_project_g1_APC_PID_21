package com.example.demo;

public class Talent {
    
    private Long id;
    private String name;
    private String email;
    private String skills;
    
    public Talent() {}
    
    // Constructor with all fields
    public Talent(String name, String email, String skills) {
        this.name = name;
        this.email = email;
        this.skills = skills;
    }
    
    // Constructor with ID (from database)
    public Talent(Long id, String name, String email, String skills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.skills = skills;
    }
    
    // Get methods
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSkills() { return skills; }
    
    // Set methods
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setSkills(String skills) { this.skills = skills; }
}


