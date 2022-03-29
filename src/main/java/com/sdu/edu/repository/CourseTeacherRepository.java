package com.sdu.edu.repository;

import com.sdu.edu.models.CourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Long> {
}
