package com.nani.spring.security.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nani.spring.security.usersservice.UsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 private final JwtFilter jwtFilter;

	    public SecurityConfig(JwtFilter jwtFilter) {
	        this.jwtFilter = jwtFilter;
	    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/nani/hi", "/user/save", "/user/authenticate").permitAll()
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

	@Bean
	public UserDetailsService detailsService() {
		return new UsersService();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService detailsService, PasswordEncoder encoder) {

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(detailsService);
		authenticationProvider.setPasswordEncoder(encoder);
		return new ProviderManager(authenticationProvider);

	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
