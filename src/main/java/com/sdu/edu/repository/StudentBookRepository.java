package com.sdu.edu.repository;

import com.sdu.edu.models.StudentBook;
import com.sdu.edu.service.StudentService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentBookRepository extends JpaRepository<StudentBook, Long> {
    boolean existsByBookIdAndStudentId(Long bookId, Long id);
    StudentBook findByBookIdAndStudentId(Long bookId, Long id);
    List<StudentBook> findAllByStudentId(Long id);
}
