package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ToDoDto;
import com.example.demo.exceptions.ToDoNotFoundException;
import com.example.demo.persistence.domain.ToDo;
import com.example.demo.persistence.repo.ToDoRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class ToDoService {

	// this is where our business logic will happen

//	this is also where CRUD logic will take place.

	// implements are crud functionality
	private ToDoRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private ToDoDto mapToDTO(ToDo toDo) {
		return this.mapper.map(toDo, ToDoDto.class);
	}

	@Autowired
	public ToDoService(ToDoRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public ToDoDto create(ToDo toDo) {
		return this.mapToDTO(this.repo.save(toDo));
	}

	// read all method
	public List<ToDoDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		// stream - returns a sequential stream considering collection as its source
		// map - used to map each element to its corresponding result
		// :: - for each e.g. Random random = new Random();
		// random.ints().limit(10).forEach(System.out::println);
		// Collectors - used to return a list or string
	}

	// read one method
	public ToDoDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(ToDoNotFoundException::new));
	}

	// update
	public ToDoDto update(ToDoDto toDoDto, Long id) {
		// check if record exists
		ToDo toUpdate = this.repo.findById(id).orElseThrow(ToDoNotFoundException::new);
		// set the record to update
		toUpdate.setTitle(toDoDto.getTitle());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(toDoDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}

	// what happenes when you try to merge null stuff?

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);// true
		return !this.repo.existsById(id);// true
	}



}