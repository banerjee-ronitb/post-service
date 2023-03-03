package com.example.postservice.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.postservice.dto.PostRequest;
import com.example.postservice.models.Post;
import com.example.postservice.services.PostService;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;


	@GetMapping("/posts")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public Flux<Post> getPosts(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
		log.info(auth);
		return postService.getPostsByUsername(getUsername());
	}

	@PostMapping("/posts")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public Mono<ResponseEntity<Post>> createPost(@RequestBody PostRequest postReq) {

		return postService.createPost(getUsername(), postReq)
		          .map(post -> ResponseEntity.ok(post))
		          .defaultIfEmpty(ResponseEntity.notFound().build());

	}
	
	@PostMapping("/posts/in")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public Flux<ResponseEntity<Post>> getPostsById(@RequestBody List<String> ids){
		return postService.getPostsById(ids)
				.map(posts -> ResponseEntity.ok(posts))
				.defaultIfEmpty(ResponseEntity.internalServerError().build());
		
	}

	@DeleteMapping("/post/{id}")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public void deletePost(@PathVariable("id") String id, @AuthenticationPrincipal Principal user) {
		postService.deletePost(id, user.getName());
	}

	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
