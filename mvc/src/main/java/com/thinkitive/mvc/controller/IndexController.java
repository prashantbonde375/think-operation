package com.thinkitive.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.thinkitive.mvc.client.CollegeClient;
import com.thinkitive.mvc.client.StudentClient;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ui")
public class IndexController {

    private final String GATEWAY_URL = "http://localhost:8080";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private CollegeClient collegeClient;

    // Using FEIGN CLIENT
    @GetMapping("/colleges")
    public String getAllColleges(Model model, HttpSession session) {
        System.out.println("🔑 Session ID (colleges): " + session.getId());

        String token = (String) session.getAttribute("jwt");
        System.out.println("🎯 Token fetched from session: " + token);

        if (token == null) {
            System.out.println("⚠️ JWT missing — redirecting to login");
            return "redirect:/ui/login";
        }

        List<?> colleges = collegeClient.getAllColleges();
        model.addAttribute("colleges", colleges);
        return "college-list";
    }

    // ✅ DELETE: Delete a college
    @GetMapping("/college/delete/{id}")
    public String deleteCollege(@PathVariable Long id) {
        restTemplate.delete(GATEWAY_URL + "/api/college/" + id);
        return "redirect:/ui/colleges";
    }

    // Using FEIGN CLIENT
    @GetMapping("/students")
    public String getAllStudents(Model model) {
        List<?> students = studentClient.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    // ✅ GET: Get all students by college ID
    @GetMapping("/students/college/{collegeId}")
    public String getStudentsByCollege(@PathVariable Long collegeId, Model model) {
        List<?> students = restTemplate.getForObject(GATEWAY_URL + "/api/student/college/" + collegeId, List.class);
        model.addAttribute("students", students);
        model.addAttribute("collegeId", collegeId);
        return "students"; // View: students.html
    }

    @PostMapping("/student/add")
    public String addStudent(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String course,
            @RequestParam Long collegeId,
            HttpSession session) {

        Map<String, Object> student = Map.of(
                "name", name,
                "email", email,
                "course", course,
                "collegeId", collegeId);

        String token = (String) session.getAttribute("jwt");
        System.out.println("🎯 Adding Student with token: " + token);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(student, headers);

        restTemplate.postForObject("http://localhost:8080/api/student", request, Map.class);

        return "redirect:/ui/students";
    }

    @PostMapping("/student/update/{id}")
    public String updateStudent(@PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String course,
            @RequestParam Long collegeId,
            HttpSession session) {

        Map<String, Object> student = new HashMap<>();
        student.put("name", name);
        student.put("email", email);
        student.put("course", course);
        student.put("collegeId", collegeId);

        // 🔑 Fetch JWT token from session
        String token = (String) session.getAttribute("jwt");
        System.out.println("🎯 Updating Student with token: " + token);

        // ✅ Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Create request entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(student, headers);

        // ✅ Use exchange() instead of put() to attach headers
        restTemplate.exchange(GATEWAY_URL + "/api/student/" + id,
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        return "redirect:/ui/students";
    }

}
