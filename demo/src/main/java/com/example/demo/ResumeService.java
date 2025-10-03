package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class ResumeService {

    // Make HTML resume from user data
    public String generateResumeHTMLFromUser(User user) {
        String html = "";
        
        // Start HTML
        html += "<!DOCTYPE html>";
        html += "<html>";
        html += "<head>";
        html += "<title>Resume - " + cleanText(user.getFullName()) + "</title>";
        html += "<style>" + getCSS() + "</style>";
        html += "</head>";
        html += "<body>";
        html += "<div class='resume-container'>";

        // Add name and contact info
        html += "<div class='header'>";
        html += "<h1>" + cleanText(user.getFullName()) + "</h1>";
        html += "<div class='contact-info'>";
        
        if (user.getEmail() != null) {
            html += "<div><strong>Email:</strong> " + cleanText(user.getEmail()) + "</div>";
        }
        if (user.getPhone() != null) {
            html += "<div><strong>Phone:</strong> " + cleanText(user.getPhone()) + "</div>";
        }
        if (user.getAddress() != null) {
            html += "<div><strong>Address:</strong> " + cleanText(user.getAddress()) + "</div>";
        }
        if (user.getLinkedin() != null) {
            html += "<div><strong>LinkedIn:</strong> " + cleanText(user.getLinkedin()) + "</div>";
        }
        if (user.getWebsite() != null) {
            html += "<div><strong>Website:</strong> " + cleanText(user.getWebsite()) + "</div>";
        }
        
        html += "</div>";
        html += "</div>";

        // Add summary
        if (user.getSummary() != null && user.getSummary().length() > 0) {
            html += "<div class='section'>";
            html += "<h2>Summary</h2>";
            html += "<p>" + cleanText(user.getSummary()) + "</p>";
            html += "</div>";
        }

        // Add experience
        if (user.getExperience() != null && user.getExperience().length() > 0) {
            html += "<div class='section'>";
            html += "<h2>Experience</h2>";
            html += "<div>" + cleanText(user.getExperience()).replace("\n", "<br>") + "</div>";
            html += "</div>";
        }

        // Add education
        if (user.getEducation() != null && user.getEducation().length() > 0) {
            html += "<div class='section'>";
            html += "<h2>Education</h2>";
            html += "<div>" + cleanText(user.getEducation()).replace("\n", "<br>") + "</div>";
            html += "</div>";
        }

        // Add skills
        if (user.getSkillsList() != null && user.getSkillsList().size() > 0) {
            html += "<div class='section'>";
            html += "<h2>Skills</h2>";
            html += "<ul class='skills-list'>";
            for (String skill : user.getSkillsList()) {
                html += "<li>" + cleanText(skill) + "</li>";
            }
            html += "</ul>";
            html += "</div>";
        }

        // Add certifications
        if (user.getCertifications() != null && user.getCertifications().length() > 0) {
            html += "<div class='section'>";
            html += "<h2>Certifications</h2>";
            html += "<div>" + cleanText(user.getCertifications()).replace("\n", "<br>") + "</div>";
            html += "</div>";
        }

        // Add projects
        if (user.getProjects() != null && user.getProjects().length() > 0) {
            html += "<div class='section'>";
            html += "<h2>Projects</h2>";
            html += "<div>" + cleanText(user.getProjects()).replace("\n", "<br>") + "</div>";
            html += "</div>";
        }

        // End HTML
        html += "</div>";
        html += "</body>";
        html += "</html>";

        return html;
    }

    // Make CSS styles for resume
    String getCSS() {
        return "body { font-family: Arial; color: #333; background: #f5f5f5; }" +
               ".resume-container { max-width: 800px; margin: 20px auto; background: white; padding: 30px; border-radius: 8px; }" +
               ".header { text-align: center; margin-bottom: 30px; border-bottom: 2px solid blue; padding-bottom: 20px; }" +
               ".header h1 { color: blue; font-size: 2em; margin-bottom: 15px; }" +
               ".contact-info { display: flex; justify-content: center; flex-wrap: wrap; gap: 15px; }" +
               ".contact-info div { font-size: 1.1em; }" +
               ".section { margin-bottom: 25px; }" +
               ".section h2 { color: blue; font-size: 1.3em; margin-bottom: 10px; border-bottom: 1px solid #ddd; }" +
               ".skills-list { display: flex; flex-wrap: wrap; gap: 8px; list-style: none; }" +
               ".skills-list li { background: #f0f0f0; padding: 5px 10px; border-radius: 10px; border: 1px solid #ccc; }" +
               "@media print { body { background: white !important; color: black !important; } " +
               ".resume-container { margin: 0 !important; padding: 20px !important; } " +
               ".header h1, .section h2 { color: black !important; } }";
    }

    // Clean text to make it safe for HTML
    String cleanText(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;");
    }
}
