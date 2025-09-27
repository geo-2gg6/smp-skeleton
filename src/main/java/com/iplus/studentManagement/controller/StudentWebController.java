package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.entity.Student;
import com.iplus.studentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class StudentWebController {

    private final StudentService studentService;

    @Autowired
    public StudentWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ADD THIS METHOD FOR THE HOME PAGE
    @GetMapping("/")
    public String showHomePage() {
        return "index"; // This tells Thymeleaf to render /templates/index.html
    }

    // Show list of students
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudentsList());
        // Note: Your file is named 'studetns.html'. I've matched it here.
        // It's recommended to rename the file to 'students.html' and change this return value.
        return "students"; 
    }

    // Show form to create a new student
    @GetMapping("/students/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "create_student"; // Renders /templates/create_student.html
    }

    // Handle form submission for creating a student
    @PostMapping("/students")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "create_student";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // Show form to edit a student
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student"; // Renders /templates/edit_student.html
    }

    // Handle form submission for updating a student
    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // If validation fails, return to the edit form
            return "edit_student";
        }

        // Set the ID from the path variable to ensure we update the correct student
        student.setId(id); 
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // Delete a student
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}