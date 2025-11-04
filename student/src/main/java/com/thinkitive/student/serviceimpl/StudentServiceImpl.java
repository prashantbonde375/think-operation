package com.thinkitive.student.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkitive.student.model.Student;
import com.thinkitive.student.repository.StudentRepository;
import com.thinkitive.student.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAll() {
        // TODO Auto-generated method stub
        return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<Student> getByCollege(Long collegeId) {
        // TODO Auto-generated method stub
        return studentRepository.findByCollegeId(collegeId);
    }

    @Override
    public Student save(Student student) {
        // TODO Auto-generated method stub
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long id, Student updated) {
        // TODO Auto-generated method stub
        Student student = getById(id);
        student.setName(updated.getName());
        student.setEmail(updated.getEmail());
        student.setCourse(updated.getCourse());
        student.setCollegeId(updated.getCollegeId());
        return studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        studentRepository.deleteById(id);
    }
}
