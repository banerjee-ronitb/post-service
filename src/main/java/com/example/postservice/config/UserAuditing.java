package com.example.postservice.config;

import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.publisher.Mono;

public class UserAuditing implements ReactiveAuditorAware<String> {

	@Override
	public Mono<String> getCurrentAuditor() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Mono.just(username);
	}

}
