package com.sdu.edu.repository;

import com.sdu.edu.models.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
    int countAllByCourseId(Long id);
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
    CourseStudent findCourseStudentByCourseIdAndStudentId(Long courseId, Long studentId);
    List<CourseStudent> findAllByStudentId(Long id);
    List<CourseStudent> findAllByCourseId(Long id);
}
