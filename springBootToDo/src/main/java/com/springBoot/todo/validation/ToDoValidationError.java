/**
 * 
 */
package com.springBoot.todo.validation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

/**
 * @author gauravagrwl
 *
 */
public class ToDoValidationError {
	
	@JsonInclude(content = Include.NON_EMPTY)
	private List<String> errors = new ArrayList<>();
	
	@Getter
	private final String errorMessage;
	
	public ToDoValidationError (String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void addValidationError(String error) {
		errors.add(error);
	}
	

}
