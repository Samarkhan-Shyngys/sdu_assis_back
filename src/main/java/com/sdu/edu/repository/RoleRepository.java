package com.sdu.edu.repository;

import java.util.Optional;

import com.sdu.edu.models.ERole;
import com.sdu.edu.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
