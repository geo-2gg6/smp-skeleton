package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.entity.Student;
import com.iplus.studentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    private final StudentService studentService;

    @Autowired
    public StudentApiController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * GET /api/students?page=0&size=5&sort=firstName,asc
     * Retrieves a paginated list of students.
     */
    @GetMapping
    public Page<Student> getAllStudents(Pageable pageable) {
        // Correctly call the method on the 'studentService' instance variable
        return studentService.getAllStudents(pageable); //
    }

    /**
     * GET /api/students/{id}
     * Retrieves a single student by their ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * POST /api/students
     * Creates a new student.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    /**
     * PUT /api/students/{id}
     * Updates an existing student.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) {
        // First, retrieve the existing student
        Student existingStudent = studentService.getStudentById(id);

        // Update the fields
        existingStudent.setFirstName(studentDetails.getFirstName());
        existingStudent.setLastName(studentDetails.getLastName());
        existingStudent.setEmail(studentDetails.getEmail());
        
        // Save the updated student
        Student updatedStudent = studentService.saveStudent(existingStudent);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * DELETE /api/students/{id}
     * Deletes a student.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student with ID " + id + " was deleted successfully.");
    }
}