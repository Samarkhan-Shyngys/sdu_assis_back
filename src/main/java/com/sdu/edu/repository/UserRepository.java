package com.sdu.edu.repository;

import java.util.Optional;

import com.sdu.edu.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	User findByEmail(String email);
	User findByActivateCode(String code);
}
