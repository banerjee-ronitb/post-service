package com.example.postservice.messaging;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class UserPostEvent implements Event{
	
	private UUID eventId;
	
	private Date date;
	
	private String postId;
	
	private Instant createdAt;
	
	private Instant lastModifiedAt;
	
	private String lastModifiedBy;
	
	private String caption;
	
	private String imageUrl;
	
	private String username;
	
	private PostEventType eventType;

	@Override
	public UUID getEventId() {
		return eventId;
	}

	@Override
	public Date getDate() {
		return date;
	}

}
