package com.example.postservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableReactiveMongoAuditing
@EnableWebSecurity
public class SecurityConfig {

	@Value("${okta.oauth2.jwk-uri}")
	private String oktaJwkUri;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and()
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwkSetUri(oktaJwkUri)));
		return http.build();
	}

	@Bean
	public ReactiveAuditorAware<String> myAuditorProvider() {
		return new UserAuditing();
	}
}
