package com.example.mapping.restapimanytomanydemo.services.interfaces;

import com.example.mapping.restapimanytomanydemo.models.Course;
import com.example.mapping.restapimanytomanydemo.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student saveStudent(Student student);
    Student getStudentById(Long id);
    List<Student> getStudentByName(String name);
    List<Student> getAllStudentsByCourseId(Long courseId);
    List<Student> getAllStudents();
    Course addCourseToStudent(Long studentId, Long courseId);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
    void removeCourseFromStudent(Long studentId, Long courseId);
    void removeAllCoursesFromStudent(Long studentId);
    void deleteAllStudents();
}