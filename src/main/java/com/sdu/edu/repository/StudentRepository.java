package com.sdu.edu.repository;

import com.sdu.edu.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long id);
    boolean existsByUserId(Long id);

}
