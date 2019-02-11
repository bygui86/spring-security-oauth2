package com.rabbit.samples.springsecurityoauth2.authservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// AUTHENTICATION SERVER
// Spring Security
@EnableWebSecurity
// relaated to resource-service, not needed anymore
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// spring security
	@Autowired
	private PasswordEncoder passwordEncoder;

	// alias UserDetailsService
	@Autowired
	private InMemoryUserDetailsManager userDetailsService;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		userDetailsService.createUser(
				User.builder()
						.username("user")
						.password(getEncodedPassword("user"))
						.authorities("USER")
						.build()
		);

		userDetailsService.createUser(
				User.builder()
						.username("admin")
						.password(getEncodedPassword("admin"))
						.authorities("ADMIN")
						.build()
		);

		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
		;
	}

	// spring security
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// spring security
	// alias UserDetailsService
	@Bean
	protected InMemoryUserDetailsManager userDetailManager() {
		return new InMemoryUserDetailsManager();
	}

	// REQUIRED by the priority bean loading
	// TODO test with @Order spring annotation
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	private String getEncodedPassword(String password) {

		return passwordEncoder.encode(password);
	}

}
