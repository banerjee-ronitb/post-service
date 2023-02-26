package com.example.postservice.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.postservice.messaging.UserPostEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class EventConfig {
	
	@Bean
	public Sinks.Many<UserPostEvent> postEventSink() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean
	public Supplier<Flux<UserPostEvent>> postSupplier(Sinks.Many<UserPostEvent> postEventSink){
		return postEventSink :: asFlux;
	}
}
