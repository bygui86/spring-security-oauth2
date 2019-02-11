package com.rabbit.samples.springsecurityoauth2.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
// spring security oauth
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// resource-service name and type
	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {

		resources
				.resourceId("hybris")
				.stateless(true);
	}

	// spring security
	@Override
	public void configure(final HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.authorizeRequests()
				.anyRequest()
				.authenticated();
				// .hasRole("CUSTOMERGROUP");

	}

	// @Bean
	// public RestTemplate oAuth2RestTemplate() {
	//
	// 	final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext());
	// 	//oAuth2RestTemplate.setRequestFactory(new Netty4ClientHttpRequestFactory());
	// 	return oAuth2RestTemplate;
	//
	// }
}
