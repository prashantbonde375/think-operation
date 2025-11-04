package com.thinkitive.mvc.controller;

import com.thinkitive.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/ui")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    private final String AUTH_URL = "http://localhost:8080/api/auth";

    // --- Show login page ---
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // --- Show register page ---
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // --- Register new user ---
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            restTemplate.postForObject(AUTH_URL + "/register", user, String.class);
            model.addAttribute("msg", "Registration successful! Please login.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("msg", "Registration failed!");
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        try {
            // Prepare user payload
            Map<String, String> request = Map.of(
                    "username", username,
                    "password", password);

            // Send POST request to Auth Service
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    AUTH_URL + "/login", request, Map.class);

            // Handle response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String token = (String) response.getBody().get("token");
                session.setAttribute("jwt", token);
                System.out.println("JWT stored in session: " + token);

                return "redirect:/ui/colleges";
            } else {
                model.addAttribute("msg", "Invalid credentials!");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "Login failed: " + e.getMessage());
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("👋 User logged out. Session invalidated.");

        return "redirect:/ui/login";
    }

}
