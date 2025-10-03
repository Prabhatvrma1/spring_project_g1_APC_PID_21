package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//i serve html pages
@Controller
public class HomeController {

    // Home page
    @RequestMapping("/")
    public String index(){
         return "redirect:/login";
     }

    // Login page
    @RequestMapping("/login")
    public String login(){
         return "redirect:/login.html";
     }

    // Signup page
    @RequestMapping("/signup")
    public String signup(){
         return "redirect:/signup.html";
     }

    // Resume builder page
    @RequestMapping("/resume-builder")
    public String resumeBuilder(){
         return "redirect:/resume-builder.html";
     }

}
