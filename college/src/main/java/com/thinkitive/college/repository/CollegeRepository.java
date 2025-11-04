package com.thinkitive.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thinkitive.college.model.College;

public interface CollegeRepository extends JpaRepository<College, Long>{
    
}
