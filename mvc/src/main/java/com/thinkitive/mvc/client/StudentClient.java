package com.thinkitive.mvc.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thinkitive.mvc.configuration.FeignConfig;

@FeignClient(name = "gateway", configuration = FeignConfig.class)
public interface StudentClient {

    @GetMapping("/api/student")
    List<Object> getAllStudents();

    @GetMapping("/api/student/college/{collegeId}")
    List<Object> getStudentsByCollege(@PathVariable("collegeId") Long collegeId);

    @PostMapping("/api/student")
    Object createStudent(@RequestBody Map<String, Object> student);

    @PutMapping("/api/student/{id}")
    Object updateStudent(@PathVariable("id") Long id, @RequestBody Map<String, Object> student);

    @DeleteMapping("/api/student/{id}")
    void deleteStudent(@PathVariable("id") Long id);
}
