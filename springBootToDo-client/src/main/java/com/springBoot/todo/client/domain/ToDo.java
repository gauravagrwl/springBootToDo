package com.springBoot.todo.client.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ToDo {
	
	@NotNull
	private String id;
	
	@NotNull
	@NotBlank
	private String description;
	
	private boolean completed;
	
	private LocalDateTime created;
	
	private LocalDateTime modified;
	
	public ToDo() {
		LocalDateTime date = LocalDateTime.now();
		this.id = UUID.randomUUID().toString();
		this.created = this.modified = date;
	}
	
	public ToDo (String description) {
		this();
		this.description = description;
	}

}
