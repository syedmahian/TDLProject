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

import com.example.demo.dto.UserDto;
import com.example.demo.persistence.domain.User;
import com.example.demo.service.UserService;


@SpringBootTest // spring boot test lets spring know this is a test file and treat as such
@ActiveProfiles("dev") // lets us set the application porperties file.
public class UserControllerUnitTest {

	@Autowired
	private UserController controller;

	@MockBean
	private UserService service;

	@Autowired
	private ModelMapper mapper;

	// same thing from our service
	private UserDto maptoDto(User user) {
		return this.mapper.map(user, UserDto.class);
	}

	// During our test we want give it some data to use
	private final User TEST_USER_1 = new User(1L, "Name 1");
	private final User TEST_USER_2 = new User(2L, "Name 2");
	private final User Test_USER_3 = new User(3L, "Name 3");
	private final User Test_USER_4 = new User(4L, "Name 4");

	// I also want to create a list of cars that i can use later
	private final List<User> LISTOFUSERS = List.of(TEST_USER_1, TEST_USER_2, Test_USER_3, Test_USER_4);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_USER_1)).thenReturn(this.maptoDto(TEST_USER_1));
		assertThat(new ResponseEntity<UserDto>(this.maptoDto(TEST_USER_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_USER_1));
		verify(this.service, atLeastOnce()).create(TEST_USER_1);

	}

	// Read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_USER_1.getId())).thenReturn(this.maptoDto(TEST_USER_1));
		assertThat(new ResponseEntity<UserDto>(this.maptoDto(TEST_USER_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_USER_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_USER_1.getId());
	}

	// Read all
	@Test
	void readAllTest() throws Exception {
		List<UserDto> dtos = LISTOFUSERS.stream().map(this::maptoDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// Update
	@Test
	void UpdateTest() throws Exception {
		when(this.service.update(this.maptoDto(TEST_USER_1), TEST_USER_1.getId())).thenReturn(this.maptoDto(TEST_USER_1));
		assertThat(new ResponseEntity<UserDto>(this.maptoDto(TEST_USER_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_USER_1.getId(), this.maptoDto(TEST_USER_1)));
		verify(this.service, atLeastOnce()).update(this.maptoDto(TEST_USER_1), TEST_USER_1.getId());
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_USER_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_USER_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT));
		verify(this.service, atLeastOnce()).delete(TEST_USER_1.getId());

	}

	@Test
	void deleteTest2() throws Exception {
		when(this.service.delete(TEST_USER_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_USER_1.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_USER_1.getId());

	}

	
}