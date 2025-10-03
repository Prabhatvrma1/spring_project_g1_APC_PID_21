package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TalentService talentService;

    @Autowired
    SessionService sessionService;

    // Helper method to check if user is logged in
    private Map<String, Object> checkAuth(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = sessionService.getCurrentUser(session);
        if (user == null) {
            result.put("success", false);
            result.put("message", "Please login first");
        }
        result.put("user", user);
        return result;
    }

    // Make resume from user data
    @GetMapping("/generate")
    public Map<String, Object> makeResume(HttpSession session) {
        Map<String, Object> authCheck = checkAuth(session);
        User user = (User) authCheck.get("user");
        if (user == null) {
            return authCheck;
        }

        Map<String, Object> result = new HashMap<>();
        String html = resumeService.generateResumeHTMLFromUser(user);
        result.put("success", true);
        result.put("resumeHTML", html);
        return result;
    }

    // Show resume preview
    @PostMapping("/preview")
    public Map<String, Object> showPreview(@RequestBody User userData) {
        Map<String, Object> result = new HashMap<>();
        String html = resumeService.generateResumeHTMLFromUser(userData);
        result.put("success", true);
        result.put("resumeHTML", html);
        return result;
    }

    // Save user profile
    @PutMapping("/profile")
    public Map<String, Object> saveProfile(@RequestBody User newData, HttpSession session) {
        Map<String, Object> authCheck = checkAuth(session);
        User user = (User) authCheck.get("user");
        if (user == null) {
            return authCheck;
        }

        // Copy new data to user
        user.setPhone(newData.getPhone());
        user.setAddress(newData.getAddress());
        user.setLinkedin(newData.getLinkedin());
        user.setWebsite(newData.getWebsite());
        user.setSummary(newData.getSummary());
        user.setExperience(newData.getExperience());
        user.setEducation(newData.getEducation());
        user.setCertifications(newData.getCertifications());
        user.setProjects(newData.getProjects());
        user.setSkillsList(newData.getSkillsList());

        userDAO.updateProfile(user);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Profile saved!");
        return result;
    }

    // Turn resume into talent
    @PostMapping("/convert-to-talent")
    public Map<String, Object> makeTalent(HttpSession session) {
        Map<String, Object> authCheck = checkAuth(session);
        User user = (User) authCheck.get("user");
        if (user == null) {
            return authCheck;
        }

        Talent talent = new Talent();
        talent.setName(user.getFullName());
        talent.setEmail(user.getEmail());
        
        String allSkills = (user.getSkillsList() != null && user.getSkillsList().size() > 0) 
            ? String.join(", ", user.getSkillsList()) 
            : "No skills added yet";
        talent.setSkills(allSkills);

        Talent savedTalent = talentService.createTalent(talent);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Made talent from resume!");
        result.put("talentId", savedTalent.getId());
        result.put("name", savedTalent.getName());
        result.put("email", savedTalent.getEmail());
        result.put("skills", savedTalent.getSkills());
        return result;
    }

    // Copy talent data to resume
    @PostMapping("/import-talent")
    public Map<String, Object> copyTalent(@RequestBody Map<String, Long> request, HttpSession session) {
        Map<String, Object> authCheck = checkAuth(session);
        User user = (User) authCheck.get("user");
        if (user == null) {
            return authCheck;
        }

        Long talentId = request.get("talentId");
        if (talentId == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Need talent ID");
            return result;
        }

        Talent talent = talentService.getTalentById(talentId);
        if (talent == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Talent not found");
            return result;
        }

        user.setFullName(talent.getName());
        user.setEmail(talent.getEmail());
        
        if (talent.getSkills() != null && talent.getSkills().length() > 0) {
            String[] skillsArray = talent.getSkills().split(",");
            List<String> skillsList = new ArrayList<>();
            for (String skill : skillsArray) {
                skillsList.add(skill.trim());
            }
            user.setSkillsList(skillsList);
        }

        userDAO.updateProfile(user);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Copied talent data to resume!");
        result.put("name", talent.getName());
        result.put("email", talent.getEmail());
        result.put("skills", talent.getSkills());
        return result;
    }

    // Get all talents
    @GetMapping("/available-talents")
    public Map<String, Object> getAllTalents(HttpSession session) {
        Map<String, Object> authCheck = checkAuth(session);
        User user = (User) authCheck.get("user");
        if (user == null) {
            return authCheck;
        }

        List<Talent> talents = talentService.getAllTalents();
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("talents", talents);
        return result;
    }

    // Get user profile
    @GetMapping("/profile")
    public Map<String, Object> getUserProfile(HttpSession session) {
        Map<String, Object> authCheck = checkAuth(session);
        User user = (User) authCheck.get("user");
        if (user == null) {
            return authCheck;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("user", user);
        return result;
    }
}
