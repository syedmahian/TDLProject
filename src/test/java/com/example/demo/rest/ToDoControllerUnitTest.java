package com.example.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.ToDoDto;
import com.example.demo.persistence.domain.ToDo;
import com.example.demo.service.ToDoService;

@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application porperties file.
public class ToDoControllerUnitTest {

	@Autowired
	private ToDoController controller;

	@MockBean
	private ToDoService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private ToDoDto maptoDto(ToDo toDo) {
		return this.mapper.map(toDo, ToDoDto.class);
	}

	// During our test we want give it some data to use
	private final ToDo TEST_TODO_1 = new ToDo(1L, "Test Title 1", "Test Task 1");
	private final ToDo TEST_TODO_2 = new ToDo(2L, "Test Title 2", "Test Title 2");
	private final ToDo Test_TODO_3 = new ToDo(3L, "Test Title 3", "Test Task 3");
	private final ToDo Test_TODO_4 = new ToDo(4L, "Test Title 4", "Test Title 4");

	// I also want to create a list of cars that i can use later
	private final List<ToDo> LISTOFCARS = List.of(TEST_TODO_1, TEST_TODO_2, Test_TODO_3, Test_TODO_4);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_TODO_1)).thenReturn(this.maptoDto(TEST_TODO_1));
		assertThat(new ResponseEntity<ToDoDto>(this.maptoDto(TEST_TODO_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_TODO_1));
		verify(this.service, atLeastOnce()).create(TEST_TODO_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_TODO_1.getId())).thenReturn(this.maptoDto(TEST_TODO_1));
		assertThat(new ResponseEntity<ToDoDto>(this.maptoDto(TEST_TODO_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_TODO_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_TODO_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<ToDoDto> dtos = LISTOFCARS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_TODO_1), TEST_TODO_1.getId())).thenReturn(this.maptoDto(TEST_TODO_1));
		assertThat(new ResponseEntity<ToDoDto>(this.maptoDto(TEST_TODO_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_TODO_1.getId(), this.maptoDto(TEST_TODO_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_TODO_1), TEST_TODO_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_TODO_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_TODO_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT));
		verify(this.service, atLeastOnce()).delete(TEST_TODO_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_TODO_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_TODO_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_TODO_1.getId());

	}

	
}