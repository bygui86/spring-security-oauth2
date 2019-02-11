package com.rabbit.samples.springsecurityoauth2.authservice.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// resource-service simulator endpoints for preliminary testing purposes
// not needed anymore
@RestController
public class HelloController {

	@GetMapping(value = "/hello")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	// @PreAuthorize("#oauth2.hasScope('hello')")
	public String hello() {

		return "Hello World!";
	}
}
