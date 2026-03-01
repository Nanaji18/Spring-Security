package com.nani.spring.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nani.spring.security.entity.Users;
import com.nani.spring.security.repository.UserRepo;

@RestController
@RequestMapping("/user")
public class UsersController {
	
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/save")
	public Users saveUser(@RequestParam String username, @RequestParam String password) {
		
		Users user=new Users();
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setActive(true);
		return repo.save(user);
	}
	
	@GetMapping("/getusers")
	public List<Users> getUsers(){
		return repo.findAll();
	}

}
