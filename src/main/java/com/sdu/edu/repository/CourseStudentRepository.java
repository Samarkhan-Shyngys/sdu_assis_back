package com.sdu.edu.repository;

import com.sdu.edu.models.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
}
