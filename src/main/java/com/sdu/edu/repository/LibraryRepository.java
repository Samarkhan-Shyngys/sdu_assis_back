package com.sdu.edu.repository;

import com.sdu.edu.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    List<Library> findAll();
}
