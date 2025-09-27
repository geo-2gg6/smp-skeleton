package com.iplus.studentManagement.service;

import com.iplus.studentManagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    // Get all students with pagination
    Page<Student> getAllStudents(Pageable pageable);

    // Get all students as a list (for web views)
    List<Student> getAllStudentsList();

    // Get a single student by ID
    Student getStudentById(Long id);

    // Create or update a student
    Student saveStudent(Student student);

    // Delete a student by ID
    void deleteStudent(Long id);
}
