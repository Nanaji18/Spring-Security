package com.nani.spring.security.usersservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nani.spring.security.entity.Users;
import com.nani.spring.security.repository.UserRepo;

@Service
public class UsersService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	public Users getUserNameAndInactive(String username, Boolean active) {
		return userRepo.findByUsernameAndActive(username,active).orElseThrow(()->new UsernameNotFoundException("username not found"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Users userNameAndInactive = getUserNameAndInactive(username,true);
	
	return User.builder().username(userNameAndInactive.getUsername()).password(userNameAndInactive.getPassword()).authorities(new SimpleGrantedAuthority(userNameAndInactive.getRole())).build();
	}

}
