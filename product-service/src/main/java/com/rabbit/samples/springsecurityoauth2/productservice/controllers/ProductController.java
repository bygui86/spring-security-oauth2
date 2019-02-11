package com.rabbit.samples.springsecurityoauth2.productservice.controllers;

import com.rabbit.samples.springsecurityoauth2.productservice.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

	// we could use also some annotations, or as is using a specific config class
	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable String id) {

		return new Product(id, "product");
	}
}
