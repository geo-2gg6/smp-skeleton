package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.dto.StudentDto;
import com.iplus.studentManagement.entity.Student;
import com.iplus.studentManagement.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    private final StudentService studentService;

    public StudentWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Show list of students
    @GetMapping
    public String listStudents(Model model) {
        // 1. Get entities from service
        List<Student> studentEntities = studentService.getAllStudentsList();
        // 2. Convert entities to DTOs for the view
        List<StudentDto> studentDtos = studentEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        model.addAttribute("students", studentDtos);
        return "students"; // Thymeleaf template: /templates/students.html
    }

    // Show form to create a new student
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentDto());
        return "create_student"; // /templates/create_student.html
    }

    // Handle form submission for creating a student
    @PostMapping
    public String createStudent(@Valid @ModelAttribute("student") StudentDto studentDto, BindingResult result) {
        if (result.hasErrors()) {
            return "create_student";
        }
        // 1. Convert DTO from form to an entity
        Student studentEntity = convertToEntity(studentDto);
        // 2. Save the entity using the service
        studentService.saveStudent(studentEntity);
        return "redirect:/students";
    }

    // Show form to edit a student
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 1. Get the entity from the service
        Student studentEntity = studentService.getStudentById(id);
        // 2. Convert entity to DTO for the view
        StudentDto studentDto = convertToDto(studentEntity);
        model.addAttribute("student", studentDto);
        return "edit_student"; // /templates/edit_student.html
    }

    // Handle form submission for updating a student
    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("student") StudentDto studentDto, BindingResult result) {
        if (result.hasErrors()) {
            // If there are validation errors, we need to return to the edit form
            // The ID is not in the DTO, so we set it to ensure the form action is correct
            studentDto.setId(id);
            return "edit_student";
        }

        // 1. Get the existing entity from the database
        Student existingStudent = studentService.getStudentById(id);
        // 2. Update its fields from the DTO
        existingStudent.setFirstName(studentDto.getFirstName());
        existingStudent.setLastName(studentDto.getLastName());
        existingStudent.setEmail(studentDto.getEmail());
        // 3. Save the updated entity
        studentService.saveStudent(existingStudent);
        return "redirect:/students";
    }

    // Delete a student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // --- Helper Methods for Conversion ---

    private StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        return dto;
    }

    private Student convertToEntity(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId()); // Set ID for updates
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        return student;
    }
}