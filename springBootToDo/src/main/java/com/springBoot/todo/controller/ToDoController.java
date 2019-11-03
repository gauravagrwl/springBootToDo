/**
 * 
 */
package com.springBoot.todo.controller;

import java.awt.List;
import java.net.URI;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springBoot.todo.domain.CommonRepository;
import com.springBoot.todo.domain.ToDo;
import com.springBoot.todo.domain.TodoBuilder;
import com.springBoot.todo.validation.ToDoValidationError;
import com.springBoot.todo.validation.ToDoValidationErrorBuilder;

/**
 * @author gauravagrwl
 *
 */

@RestController
@RequestMapping("/api")
public class ToDoController {
	
	private CommonRepository<ToDo> repository;
	
	@Autowired
	public ToDoController(CommonRepository<ToDo> repository) {
		this.repository = repository;
	}
	
	@GetMapping("/todo")
	public ResponseEntity<Iterable<ToDo>> getToDos(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/todo/{id}")
	public ResponseEntity<ToDo> getToDo(@PathVariable String id){
		return ResponseEntity.ok(repository.findById(id));
	}
	
	@PatchMapping("/todo/{id}")
	public ResponseEntity<ToDo> setCompleted(@PathVariable String id){
		ToDo result = repository.findById(id);
		result.setCompleted(true);
		repository.save(result);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(result.getId()).toUri();
		
		return ResponseEntity.ok().header("location", location.toString()).build();
	}

	@RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<?> createTodo(@Valid @RequestBody ToDo todo, Errors errors){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
		}
		ToDo result = repository.save(todo);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@RequestMapping(value = "/todos", method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<?> createTodo(@Valid @RequestBody ArrayList<ToDo> todos, Errors errors){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
		}
		
		todos.forEach(todo -> repository.save(todo));		
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/todo")
	public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo todo){
		repository.delete(todo);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){
		repository.delete(TodoBuilder.create().withId(id).build());
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ToDoValidationError handleException(Exception exception) {
		return new ToDoValidationError(exception.getMessage());
	}
}
