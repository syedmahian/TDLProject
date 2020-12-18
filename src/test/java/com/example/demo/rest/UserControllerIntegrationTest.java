package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.demo.dto.UserDto;
import com.example.demo.persistence.domain.ToDo;
import com.example.demo.persistence.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
// sql runs in order schema followed by data file - JH dont make the mistake
@Sql(scripts = { "classpath:tdl-schema.sql",
		"classpath:tdl-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private UserDto mapToDTO(User user) {
		return this.mapper.map(user, UserDto.class);
	}

	// During our test we want give it some data to use
	private final User TEST_user_1 = new User("name 1"); 
	private final User TEST_user_2 = new User("name 2");
	private final User TEST_user_3 = new User("name 3");
	private final List<ToDo> listOfToDos = Collections.emptyList(); 


	// I also want to create a list of cars that i can use later
	private final List<User> LISTOFUSERS = List.of(TEST_user_1, TEST_user_2, TEST_user_3);

	private final String URI = "/user";

	// Create test
	@Test
	void createTest() throws Exception {
		UserDto testDTO = mapToDTO(new User("name 1"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isCreated();

		UserDto testSavedDTO = mapToDTO(new User("name 1"));
		testSavedDTO.setId(4L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	//		this.mvc.perform(post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON))
    //				.andExpect(status().isCreated()).andExpect(content().json(testSavedDTOAsJSON));
	}
	// Update test
	@Test
		void updateTest() throws Exception {
		
		User user = new User("name 1");
		user.setToDos(listOfToDos);
		UserDto testDTO = mapToDTO(user);
		
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "/update/1").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isAccepted();

	//	UserDto testSavedDTO = mapToDTO(new User("name 1"));
		user.setId(1L);
		UserDto expected = mapToDTO(user);
		String expectedAsJSON = this.jsonifier.writeValueAsString(expected);

		ResultMatcher checkBody = content().json(expectedAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	//		this.mvc.perform(post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON))
	//		.andExpect(status().isCreated()).andExpect(content().json(testSavedDTOAsJSON));
				}


	//Read test
	@Test
	void ReadOne() throws Exception {
		User user = new User("name 1");
		user.setToDos(listOfToDos);
		UserDto testDTO = mapToDTO(user);
		
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = get(URI + "/read/1").contentType(MediaType.APPLICATION_JSON);

		ResultMatcher checkStatus = status().isOk();
		user.setId(1L);
		UserDto expected = mapToDTO(user);
		String expectedAsJSON = this.jsonifier.writeValueAsString(expected);
		ResultMatcher checkBody = content().json(expectedAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

//		this.mvc.perform(post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON))
//				.andExpect(status().isCreated()).andExpect(content().json(testSavedDTOAsJSON));
	}
	

		
		// Delete test
		@Test
		void deleteTest() throws Exception {

			RequestBuilder request = delete(URI + "/delete/1").contentType(MediaType.APPLICATION_JSON);

			ResultMatcher checkStatus = status().isNoContent();

			this.mvc.perform(request).andExpect(checkStatus);

//			this.mvc.perform(post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON))
//					.andExpect(status().isCreated()).andExpect(content().json(testSavedDTOAsJSON));
		}
}