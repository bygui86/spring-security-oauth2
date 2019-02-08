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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

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
				//
				.authorizeRequests()
				.anyRequest()
				.authenticated()
		;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected InMemoryUserDetailsManager userDetailManager() {
		return new InMemoryUserDetailsManager();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}


}
