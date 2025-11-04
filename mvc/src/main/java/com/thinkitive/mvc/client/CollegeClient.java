package com.thinkitive.mvc.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thinkitive.mvc.configuration.FeignConfig;

@FeignClient(name = "gateway", configuration = FeignConfig.class)
public interface CollegeClient {

    @GetMapping("/api/college")
    List<?> getAllColleges();

    @GetMapping("/api/college/{id}")
    Object getCollegeById(@PathVariable("id") Long id);

    @PostMapping("/api/college")
    Object createCollege(@RequestBody Map<String, Object> college);

    @DeleteMapping("/api/college/{id}")
    void deleteCollege(@PathVariable("id") Long id);
}
