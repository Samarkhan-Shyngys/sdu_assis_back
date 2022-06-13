package com.sdu.edu.repository;

import com.sdu.edu.models.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Long> {
    boolean existsByAssistentId(Long id);
    boolean existsByCourseName(String name);
    CourseTeacher findByAssistentId(Long id);
    List<CourseTeacher> findAllByAssistentId(Long id);
}
