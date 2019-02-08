package com.rabbit.samples.springsecurityoauth2.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {

		resources
				.resourceId("hybris")
				.stateless(true);
	}

	@Override
	public void configure(final HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.authorizeRequests()
				.anyRequest()
				.authenticated();
				// .hasRole("CUSTOMERGROUP");

	}
}
