package com.sdu.edu.repository;

import com.sdu.edu.models.JobMdl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<JobMdl, Long> {

    List<JobMdl> findAllByAssId(Long id);
}
