  
package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoDto {

	private Long id;
	private String title; //used to be name
	private String task; // used to be colour
	

	// this will spit out JSON

}