package com.nani.spring.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nani.spring.security.entity.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, Long>{
	
	Optional<Users> findByUsernameAndActive(String username, Boolean active);

}
