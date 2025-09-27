package com.iplus.studentManagement.controller;

import com.iplus.studentManagement.dto.StudentDto;
import com.iplus.studentManagement.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<StudentDto> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students/list"; // Thymeleaf template: src/main/resources/templates/students/list.html
    }

    // Show form to create a new student
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentDto());
        return "students/create"; // templates/students/create.html
    }

    // Handle form submission for creating student
    @PostMapping
    public String createStudent(@ModelAttribute("student") StudentDto studentDto) {
        studentService.createStudent(studentDto);
        return "redirect:/students";
    }

    // Show form to edit student
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentDto student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "students/edit"; // templates/students/edit.html
    }

    // Handle update student form
    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") StudentDto studentDto) {
        studentService.updateStudent(id, studentDto);
        return "redirect:/students";
    }

    // Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
