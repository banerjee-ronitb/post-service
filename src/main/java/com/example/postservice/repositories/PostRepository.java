package com.example.postservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.postservice.models.Post;

import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {

	Flux<Post> findByUsernameOrderByCreatedAtDesc(String username);
	
	Flux<Post> findByIdOrderByCreatedAtDesc(List<String> ids);
}
