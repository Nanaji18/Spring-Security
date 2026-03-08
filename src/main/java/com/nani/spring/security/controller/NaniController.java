package com.nani.spring.security.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/nani")
public class NaniController {
	
	@Value("${JAVA_HOME}")
	private String javaHome;
	
	@Value("${spring.application.name}")
	private String appname;
	
	
	@GetMapping("/hello")
	public String getHelloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/javaHome")
	public String getHome() {
		return javaHome;
	}
	
	@GetMapping("/appName")
	public String getAppName() {
		return appname;
	}
	
	@GetMapping("/hi")
	public String getHiWorld() {
		return "Hi World";
	}
	
	@GetMapping("/hey")
	@PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
	public String getHeyWorld() {
		return "Hey World";
	}
	
	
	@GetMapping("/heyy/{name}")
	public String getHeyWorld(@PathVariable String name) {
		return name;
	}
	
	@PostMapping("/savee")
	@PreAuthorize("hasRole('ADMIN')")
	public String saveHeyWorld() {
		return "saved";
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String getHome1() {
		return "getJava";
	}
	
	

}
