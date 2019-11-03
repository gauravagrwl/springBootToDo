package com.springBoot.todo.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springBoot.todo.client.domain.ToDo;
import com.springBoot.todo.client.error.ToDoErrorHandler;

@Service
public class ToDoRestClient {

	private RestTemplate restTemplate;
	private ToDoRestClientProperties properties;

	public ToDoRestClient(ToDoRestClientProperties properties) {
		this.restTemplate = new RestTemplate();
		this.restTemplate.setErrorHandler(new ToDoErrorHandler());
		this.properties = properties;
	}

	public Iterable<ToDo> findAll() throws URISyntaxException {
		RequestEntity<Iterable<ToDo>> requestEntity = new RequestEntity<>(HttpMethod.GET,
				new URI(properties.getUrl() + properties.getBasePath()));
		ResponseEntity<Iterable<ToDo>> responseEntity = restTemplate.exchange(requestEntity,
				new ParameterizedTypeReference<Iterable<ToDo>>() {
				});
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return responseEntity.getBody();
		}
		return null;
	}
	
	public ToDo findById(String id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		return restTemplate.getForObject(properties.getUrl()+properties.getBasePath() + "/{id}", ToDo.class, params);
	}
	
	public ToDo upsert(ToDo toDo) throws URISyntaxException {
		RequestEntity<?> requestEntity = new RequestEntity<>(toDo, HttpMethod.POST, new URI(properties.getUrl()+properties.getBasePath()));
		ResponseEntity<?> responseEntity = restTemplate.exchange(requestEntity,new ParameterizedTypeReference <ToDo>() {});
		if(responseEntity.getStatusCode() == HttpStatus.CREATED) {
			return restTemplate.getForObject(responseEntity.getHeaders().getLocation(), ToDo.class);
		}
		return null;
	}
	
	public ToDo setCompleted(String id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.patchForObject(properties.getUrl()+properties.getBasePath()+"/{id}", null, ResponseEntity.class, params);
		return findById(id);
	}

	public void delete(String id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", id);
		restTemplate.delete(properties.getUrl()+properties.getBasePath()+"/{id}", params);
		
	}
	

}





































