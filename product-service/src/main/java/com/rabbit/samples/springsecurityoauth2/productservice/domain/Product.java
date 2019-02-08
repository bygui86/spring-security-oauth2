package com.rabbit.samples.springsecurityoauth2.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Product {

	private String id;

	private String name;
}
