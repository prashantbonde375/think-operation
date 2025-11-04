package com.thinkitive.college.service;

import java.util.List;

import com.thinkitive.college.model.College;

public interface CollegeService {
    public List<College> getAll();

    public College getById(Long id);

    public College save(College college);

    public College update(Long id, College updated);

    public void delete(Long id);
}
