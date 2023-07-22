package com.example.mapping.restapimanytomanydemo.repositories;

import com.example.mapping.restapimanytomanydemo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(String name);
    List<Student> findStudentsByCoursesId(Long courseId);
}