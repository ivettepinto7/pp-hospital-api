package com.grupo25.hospital.models.dtos;

import javax.validation.constraints.NotNull;

public class EditAreaDTO {
	@NotNull
	private Long id;
	
	private String name;
	
	public EditAreaDTO() {
		super();
	}
	
	public EditAreaDTO(@NotNull Long id, String name, Integer start_age, Integer frequency) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
