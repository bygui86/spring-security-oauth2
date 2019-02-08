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

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
			.withClient("test-client")
				.secret(getEncodedPassword("client-secret"))
				.authorizedGrantTypes("refresh_token", "password")
				.authorities("ADMIN")
				.scopes("extended")
			.and()
			.withClient("microservice")
				.secret(getEncodedPassword("micro-secret"))
				.authorizedGrantTypes("client_credentials")
				.scopes("extended")
		;
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
				.authenticationManager(authenticationManager)
				.tokenStore(tokenStore)
				.userDetailsService(userDetailsService)
				.reuseRefreshTokens(true)
		;
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {

		security.allowFormAuthenticationForClients()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("permitAll()")
		;
	}

	@Bean
	protected TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}


	private String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
