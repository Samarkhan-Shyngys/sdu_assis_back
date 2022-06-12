package com.sdu.edu.repository;

import com.sdu.edu.models.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    boolean existsByUserId(Long id);
    Assistant findByUserId(Long id);

}
