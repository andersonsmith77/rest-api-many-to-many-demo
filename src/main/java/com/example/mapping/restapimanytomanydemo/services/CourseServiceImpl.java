package com.example.mapping.restapimanytomanydemo.services;

import com.example.mapping.restapimanytomanydemo.exceptions.ResourceNotFoundException;
import com.example.mapping.restapimanytomanydemo.models.Course;
import com.example.mapping.restapimanytomanydemo.repositories.CourseRepository;
import com.example.mapping.restapimanytomanydemo.services.interfaces.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    private CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepository.findByNameContaining(name);
    }

    @Override
    public List<Course> getAllCoursesByStudentId(Long studentId) {
        return courseRepository.findCoursesByStudentsId(studentId);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        return courseRepository.findById(id)
                .map(cou -> {
                    cou.setName(course.getName());
                    cou.setFee(course.getFee());

                    return courseRepository.save(cou);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}