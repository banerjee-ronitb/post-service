package com.example.postservice.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.postservice.dto.PostRequest;
import com.example.postservice.messaging.PostEventType;
import com.example.postservice.messaging.UserPostEvent;
import com.example.postservice.models.Post;
import com.example.postservice.repositories.PostRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@Slf4j	
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private Sinks.Many<UserPostEvent> postEventSink;

	public Flux<Post> getPostsByUsername(String name) {
		return postRepository.findByUsernameOrderByCreatedAtDesc(name);
	}

	@Transactional
	public Mono<Post> createPost(String username, PostRequest postReq) {
		Post p = new Post(postReq.getImageUrl(), postReq.getImageCaption());
		Mono<Post> postMono = postRepository.save(p);
		postMono.subscribe(post -> {
			UserPostEvent ev = new UserPostEvent();

			ev.setCaption(post.getCaption());
			ev.setPostId(post.getId());
			ev.setCreatedAt(post.getCreatedAt());
			ev.setDate(new Date());
			ev.setEventId(UUID.randomUUID());
			ev.setEventType(PostEventType.CREATED);
			ev.setLastModifiedBy(post.getLastModifiedBy());
			ev.setUsername(post.getUsername());
			ev.setLastModifiedAt(post.getLastModifiedAt());

			postEventSink.tryEmitNext(ev);
			log.info("Event triggred");
		});

		return postMono;

	}

	public Flux<Post> getPostsById(List<String> ids) {
		return postRepository.findByIdOrderByCreatedAtDesc(ids);
	}

	public void deletePost(String id, String name) {
		Mono<Post> post = postRepository.findById(id);
		Optional.ofNullable(post).map(p -> postRepository.delete(p.block())).orElse(null);

	}

}
