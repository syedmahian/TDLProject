package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	public Long id;
	public String fullName; 
	private List<ToDoDto> toDos = new ArrayList<>();
}