package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.ToDoNotFoundException;
import com.example.demo.persistence.domain.User;
import com.example.demo.persistence.repo.UserRepo;
import com.example.demo.util.SpringBeanUtil;

@Service
public class UserService {


	// implements are crud functionality
	private UserRepo repo;

	// makes object mapping easy by automatically determining how one object model
	// maps to another.
	private ModelMapper mapper;

	// we create our mapToDto

	private UserDto mapToDTO(User user) {
		return this.mapper.map(user, UserDto.class);
	}

	@Autowired
	public UserService(UserRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public UserDto create(User user) {
		return this.mapToDTO(this.repo.save(user));
	}

	// read all method
	public List<UserDto> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		
	}

	// read one method
	public UserDto readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(ToDoNotFoundException::new));
	}

	// update
	public UserDto update(UserDto userDto, Long id) {
		// check if record exists
		User toUpdate = this.repo.findById(id).orElseThrow(ToDoNotFoundException::new);
		// set the record to update
		toUpdate.setFullName(userDto.getFullName());
		// check update for any nulls
		SpringBeanUtil.mergeNotNull(userDto, toUpdate);
		// retun the method from repo
		return this.mapToDTO(this.repo.save(toUpdate));

	}


	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}