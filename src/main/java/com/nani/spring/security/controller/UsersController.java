package com.nani.spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nani.spring.security.entity.AuthRequest;
import com.nani.spring.security.entity.Users;
import com.nani.spring.security.repository.UserRepo;
import com.nani.spring.security.usersservice.JwtService;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/user")
public class UsersController {
	
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/save")
	public Users saveUser(@RequestParam String username, @RequestParam String password) {
		
		Users user=new Users();
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setActive(true);
		return repo.save(user);
	}
	
	@PostMapping("/authenticate")
	public String authenticatee(@RequestBody AuthRequest authRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		}
		return null;
		
	}
	
	@GetMapping("/getusers")
	public List<Users> getUsers(){
		return repo.findAll();
	}

}
