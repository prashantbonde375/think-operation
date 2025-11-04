package com.thinkitive.student.service;

import java.util.List;

import com.thinkitive.student.model.Student;

public interface StudentService {
    public List<Student> getAll();

    public Student getById(Long id);

    public List<Student> getByCollege(Long collegeId);

    public Student save(Student student);

    public Student update(Long id, Student updated);

    public void delete(Long id);
}
