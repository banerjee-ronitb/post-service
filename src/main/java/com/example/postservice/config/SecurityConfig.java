package com.example.postservice.config;

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

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().oauth2ResourceServer(
				oauth2 -> oauth2.jwt(jwt -> jwt.jwkSetUri("https://dev-63954939.okta.com/.well-known/jwks.json")));
		// http.cors();
		return http.build();
	}

	@Bean
	public ReactiveAuditorAware<String> myAuditorProvider() {
		return new UserAuditing();
	}
}
