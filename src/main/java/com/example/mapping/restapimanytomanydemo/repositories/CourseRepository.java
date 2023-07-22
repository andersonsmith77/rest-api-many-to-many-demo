package com.example.mapping.restapimanytomanydemo.repositories;

import com.example.mapping.restapimanytomanydemo.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameContaining(String name);
    List<Course> findCoursesByStudentsId(Long studentsId);
}