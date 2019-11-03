package com.springBoot.todo.domain;

public class TodoBuilder {
	
	private static TodoBuilder instance = new TodoBuilder();
	
	private String id = null;
	private String description = "";
	
	private TodoBuilder() {}
	
	public static TodoBuilder create() {
		return instance;
	}
	
	public TodoBuilder withDescription(String _description) {
		this.description = _description;
		return instance;
	}
	
	public TodoBuilder withId(String _id) {
		this.id = _id;
		return instance;
	}
	
	public ToDo build() {
		ToDo result = new ToDo(this.description);
		if(id !=null) {
			result.setId(id);
		}
		return result;
	}
	
}
