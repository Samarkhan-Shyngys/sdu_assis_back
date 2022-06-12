package com.sdu.edu.repository;

import com.sdu.edu.models.Language;
import com.sdu.edu.service.LibraryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findAllByAssisId(Long id);
}
