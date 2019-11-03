package com.springBoot.todo.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "todo")
public class ToDoRestClientProperties {
	
	private String url;
	private String basePath;
	
	

}
