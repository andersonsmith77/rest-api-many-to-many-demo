package com.example.mapping.restapimanytomanydemo.services.interfaces;

import com.example.mapping.restapimanytomanydemo.models.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    Course saveCourse(Course course);
    Course getCourseById(Long id);
    List<Course> getCoursesByName(String name);
    List<Course> getAllCoursesByStudentId(Long studentId);
    List<Course> getAllCourses();
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}