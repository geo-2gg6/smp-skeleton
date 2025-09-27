package com.iplus.studentManagement.service.impl;

import com.iplus.studentManagement.entity.student;
import com.iplus.studentManagement.exception.ResourceFoundException;
import com.iplus.studentManagement.exception.ResourceNotFoundException;
import com.iplus.studentManagement.repository.StudentRepository;
import com.iplus.studentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public List<student> getAllStudentsList() {
        return studentRepository.findAll();
    }

    @Override
    public student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    @Override
    public student saveStudent(student student) {
        // Optional: Check for duplicate email
        if (student.getId() == null && studentRepository.existsByEmail(student.getEmail())) {
            throw new ResourceFoundException("Email already exists: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
