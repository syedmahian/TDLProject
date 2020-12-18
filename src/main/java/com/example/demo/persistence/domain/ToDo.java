package com.example.demo.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classes that represent tables in DB
@Data
@NoArgsConstructor
public class ToDo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String title;

	@NotNull
	private String task;


	@ManyToOne
	private User user;

	public ToDo(Long id, String title, String task) {
		super();
		this.id = id;
		this.title = title;
		this.task = task;
		
	}

	public ToDo(String title, String task) {
		super();
		this.title = title;
		this.task = task;
		
	}

}