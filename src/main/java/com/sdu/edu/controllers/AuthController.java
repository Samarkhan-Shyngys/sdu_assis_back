package com.sdu.edu.controllers;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.sdu.edu.configs.jwt.JwtUtils;
import com.sdu.edu.models.ERole;
import com.sdu.edu.models.Role;
import com.sdu.edu.models.User;
import com.sdu.edu.pojo.*;
import com.sdu.edu.repository.RoleRepository;
import com.sdu.edu.repository.UserRepository;
import com.sdu.edu.service.UserDetailsImpl;
import com.sdu.edu.service.UserDetailsServiceImpl;
import com.sdu.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRespository;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserService userService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) throws Exception {

		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getUsername());
		if(userDetails==null){
			return new ResponseEntity<>(new MessageResponse("Не верные иин или пароль"), HttpStatus.BAD_REQUEST);
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UserDto(
								userDetails.getId(),
								userDetails.getEmail(),
								userDetails.getUsername(),
								roles.get(0).toString()));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) throws Exception {

		if(!signupRequest.getEmail().split("@")[1].equals("stu.sdu.edu.kz") || signupRequest.getEmail().split("@")[0].length()!=9){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is not SDU"));
		}
		int sduId = Integer.parseInt(signupRequest.getEmail().split("@")[0].substring(0,2));
		System.out.println(sduId);
		int year = Calendar.getInstance().get(Calendar.MONTH)>=9 ? LocalDate.now().getYear()+1-2000:LocalDate.now().getYear()-2000;

		if(sduId>=year || year-sduId>4){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: SDU email is not active"));
		}
		
		if (userRespository.existsByEmail(signupRequest.getEmail()) && userRespository.findByEmail(signupRequest.getEmail()).getActivate()==1) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is exist"));
		}
		User user;
		if(userRespository.existsByEmail(signupRequest.getEmail())){
				user = userRespository.findByEmail(signupRequest.getEmail());
		}else{
			user = new User(signupRequest.getEmail(), signupRequest.getEmail(),
					passwordEncoder.encode(signupRequest.getPassword()));
		}


		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository
				.findByName(ERole.ROLE_STUDENT)
				.orElseThrow(() -> new RuntimeException("Error, Role Student is not found"));
		roles.add(userRole);
//		if (reqRoles == null) {

//		} else {
//			reqRoles.forEach(r -> {
//				switch (r) {
//				case "admin":
//					Role adminRole = roleRepository
//						.findByName(ERole.ROLE_ADMIN)
//						.orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
//					roles.add(adminRole);
//
//					break;
//				case "assistant":
//					Role modRole = roleRepository
//						.findByName(ERole.ROLE_ASSISTENT)
//						.orElseThrow(() -> new RuntimeException("Error, Role ASSISTANT is not found"));
//					roles.add(modRole);
//
//					break;
//
//				default:
//					Role userRole = roleRepository
//						.findByName(ERole.ROLE_ASSISTENT)
//						.orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
//					roles.add(userRole);
//				}
//			});
//		}
		Random rand = new Random();
		String code = String.valueOf(rand.nextInt(9999));
		user.setRoles(roles);
		user.setActivate(0);
		user.setActivateCode(code);
		userRespository.save(user);
		userService.senderMail(code, signupRequest.getEmail());
		signupRequest.setPassword(null);
		return ResponseEntity.ok(signupRequest);
	}

	@PostMapping("/activateCode")
	public ResponseEntity<?> activateUser(@RequestBody ActivateDto activateCodeDto) {
		System.out.println(activateCodeDto);
		User user  = userService.activateUser(activateCodeDto);
		if(user==null){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: invalid code"));
		}

		List<Role> list = user.getRoles().stream().toList();
		List<String> roles = new ArrayList<>();
		roles.add(list.get(0).getName().name());


		return ResponseEntity.ok(new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getRoles().stream().toList().get(0).getName().name()));
	}


}
