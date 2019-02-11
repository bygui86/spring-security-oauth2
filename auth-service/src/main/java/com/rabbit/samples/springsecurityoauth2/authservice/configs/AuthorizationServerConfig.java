package com.rabbit.samples.springsecurityoauth2.authservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

// AUTHORISATION SERVER
// Spring OAuth
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// spring security oauth

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	// spring oauth

	@Autowired
	private TokenStore tokenStore;

	// spring security

	@Autowired
	private PasswordEncoder passwordEncoder;

	// supported oauth clients (users and services)
	// client > auth-server
	// resoruce-services > auth-server
	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()

		// token configurations
			.withClient("test-client")
				.secret(getEncodedPassword("client-secret"))
				.authorizedGrantTypes("refresh_token", "password")
				.authorities("ADMIN")
				.scopes("extended")
			.and()

		// resources configurations
			.withClient("microservice")
				.secret(getEncodedPassword("micro-secret"))
				// should be "implicit", because "client_credentials" should be used by clients and not services
				// .authorizedGrantTypes("client_credentials")
				.authorizedGrantTypes("implicit")
				.scopes("extended")
		;
	}

	// supported resource-services
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
			// spring security oauth
			// authentication
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
			// spring oauth
			// token store
				.tokenStore(tokenStore)
				.reuseRefreshTokens(true)
		;
	}

	// security for the token endpoints
	// token endpoint should not be protected otherwise is not really possible to obtain a token
	// checkToken should be protected and not public accessible
	@Override
	public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {

		security
				.allowFormAuthenticationForClients()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("permitAll()")
		;
	}

	// spring oauth
	// for sake of POC, tokens won't be persisted
	// default spring searches a db
	@Bean
	protected TokenStore tokenStore() {

		return new InMemoryTokenStore();
	}

	// spring security
	// password encoder must be manually specified
	// default is plain-text
	private String getEncodedPassword(String password) {

		return passwordEncoder.encode(password);
	}

}
