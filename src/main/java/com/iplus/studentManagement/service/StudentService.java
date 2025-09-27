package com.iplus.studentManagement.service;

import com.iplus.studentManagement.entity.student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    // Get all students with pagination
    Page<student> getAllStudents(Pageable pageable);

    // Get all students as a list (for web views)
    List<student> getAllStudentsList();

    // Get a single student by ID
    student getStudentById(Long id);

    // Create or update a student
    student saveStudent(student student);

    // Delete a student by ID
    void deleteStudent(Long id);
}
