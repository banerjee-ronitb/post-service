package com.example.postservice.dto;

import lombok.Data;

@Data
public class PostRequest {

	private String id;
	
	private String imageUrl;

	private String imageCaption;
}
