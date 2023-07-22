package com.example.mapping.restapimanytomanydemo.services;

import com.example.mapping.restapimanytomanydemo.exceptions.ResourceNotFoundException;
import com.example.mapping.restapimanytomanydemo.models.Course;
import com.example.mapping.restapimanytomanydemo.models.Student;
import com.example.mapping.restapimanytomanydemo.repositories.CourseRepository;
import com.example.mapping.restapimanytomanydemo.repositories.StudentRepository;
import com.example.mapping.restapimanytomanydemo.services.interfaces.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", id));
    }

    @Override
    public List<Student> getStudentByName(String name) {
        return studentRepository.findByNameContaining(name);
    }

    @Override
    public List<Student> getAllStudentsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course", "Id", courseId);

        return studentRepository.findStudentsByCoursesId(courseId);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Course addCourseToStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        Course courseToAdd = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        student.addCourse(courseToAdd);
        studentRepository.save(student);
        return courseToAdd;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(stu -> {
                    stu.setName(student.getName());

                    return studentRepository.save(stu);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", id));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void removeCourseFromStudent(Long studentId, Long courseId) {
        studentRepository.findById(studentId)
                .map(stu -> {
                    stu.removeCourse(stu.getCourses().stream().filter(cou -> Objects.equals(cou.getId(), courseId)).findFirst().orElse(null));

                    return studentRepository.save(stu);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", courseId));
    }

    @Override
    public void removeAllCoursesFromStudent(Long studentId) {
        studentRepository.findById(studentId)
                .map(stu -> {
                    stu.removeAllCourses();

                    return studentRepository.save(stu);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", studentId));
    }

    @Override
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}