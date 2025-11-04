package com.thinkitive.college.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkitive.college.model.College;
import com.thinkitive.college.repository.CollegeRepository;
import com.thinkitive.college.service.CollegeService;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    public List<College> getAll() {
        return collegeRepository.findAll();
    }

    public College getById(Long id) {
        return collegeRepository.findById(id).orElseThrow(() -> new RuntimeException("College not found"));
    }

    public College save(College college) {
        return collegeRepository.save(college);
    }

    public College update(Long id, College updated) {
        College college = getById(id);
        college.setName(updated.getName());
        college.setLocation(updated.getLocation());
        college.setDean(updated.getDean());
        return collegeRepository.save(college);
    }

    public void delete(Long id) {
        collegeRepository.deleteById(id);
    }
}
