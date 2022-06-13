package com.sdu.edu.repository;

import com.sdu.edu.models.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    boolean existsByCourseIdAndStudentId(Long courseId, Long stuId);
    StudentCourse findByCourseIdAndStudentId(Long courseId, Long stuId);
    List<StudentCourse> findAllByStudentId(Long id);
}
