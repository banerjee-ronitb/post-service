package com.example.postservice.models;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;

@Data
@Document(value = "posts")
public class Post {

	@Id
	private String id;
	
	@CreatedDate
	private Instant createdAt;
	
	@LastModifiedDate
	private Instant lastModifiedAt;
	
	@CreatedBy
	private String username;
	
	@LastModifiedBy
	private String lastModifiedBy;
	
	@NonNull
	private String imageUrl;
	
	@NonNull
	private String caption;	
}
