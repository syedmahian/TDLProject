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

	
	// implements are crud functionality
	private ToDoRepo repo;

	
	// maps to another.
	private ModelMapper mapper;

	// we create  mapToDto

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

	

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}



}