package com.rabbit.samples.springsecurityoauth2.authservice.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

	@GetMapping(value = "/hello")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	// @PreAuthorize("#oauth2.hasScope('hello')")
	public String hello() {

		return "Hello World!";
	}
}
