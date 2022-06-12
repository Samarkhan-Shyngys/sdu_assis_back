package com.sdu.edu.repository;

import com.sdu.edu.models.CertificateMdl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<CertificateMdl, Long> {
    List<CertificateMdl> findAllByAssId(Long id);
}