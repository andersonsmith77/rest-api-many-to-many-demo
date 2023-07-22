package com.example.mapping.restapimanytomanydemo.controllers;

import com.example.mapping.restapimanytomanydemo.models.Student;
import com.example.mapping.restapimanytomanydemo.services.interfaces.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.saveStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String name) {
        List<Student> students = new ArrayList<>();

        if(name == null)
            students.addAll(studentService.getAllStudents());
        else
            students.addAll(studentService.getStudentByName(name));

        if (students.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        return ResponseEntity.ok(students);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.updateStudent(id, student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllStudent() {
        studentService.deleteAllStudents();

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}