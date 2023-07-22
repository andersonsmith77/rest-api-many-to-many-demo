package com.example.mapping.restapimanytomanydemo.controllers;

import com.example.mapping.restapimanytomanydemo.models.Course;
import com.example.mapping.restapimanytomanydemo.models.Student;
import com.example.mapping.restapimanytomanydemo.services.interfaces.CourseService;
import com.example.mapping.restapimanytomanydemo.services.interfaces.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private CourseService courseService;
    private StudentService studentService;

    private CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.saveCourse(course));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesByStudentId(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(courseService.getAllCoursesByStudentId(studentId));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String name) {
        List<Course> courses = new ArrayList<>();

        if(name == null)
            courses.addAll(courseService.getAllCourses());
        else
            courses.addAll(courseService.getCoursesByName(name));

        if (courses.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(studentService.getAllStudentsByCourseId(courseId));
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Course> addCourseToStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(studentService.addCourseToStudent(studentId, courseId));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("students/{studentId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourseFromStudent(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        studentService.removeCourseFromStudent(studentId, courseId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/student/{studentId}/courses")
    public ResponseEntity<String> removeAllCoursesFromStudent(@PathVariable("studentId") Long studentId) {
        studentService.removeAllCoursesFromStudent(studentId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}