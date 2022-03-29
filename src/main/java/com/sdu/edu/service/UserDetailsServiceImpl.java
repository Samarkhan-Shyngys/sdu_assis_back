package com.sdu.edu.service;

import com.sdu.edu.pojo.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdu.edu.models.User;
import com.sdu.edu.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user==null){

			return null;
		}
		return UserDetailsImpl.build(user);
	}

	public ResponseEntity<?> sendEmail(User user){


		return ResponseEntity.badRequest().body(new MessageResponse("Error: 180103"));
	}

}
