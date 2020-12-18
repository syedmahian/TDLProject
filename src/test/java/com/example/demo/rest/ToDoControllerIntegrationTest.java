package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import com.example.demo.dto.ToDoDto;
import com.example.demo.persistence.domain.ToDo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc

@Sql(scripts = { "classpath:tdl-schema.sql",
		"classpath:tdl-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class ToDoControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private ToDoDto mapToDTO(ToDo toDo) {
		return this.mapper.map(toDo, ToDoDto.class);
	}

	
	private final ToDo TEST_toDo_1 = new ToDo("title 1","task 1"); 
	private final ToDo TEST_toDo_2 = new ToDo("title 2","task 2");
	private final ToDo TEST_toDo_3 = new ToDo("title 3","task 3"); 


	
	private final List<ToDo> LISTOFTODOS = List.of(TEST_toDo_1, TEST_toDo_2, TEST_toDo_3);

	private final String URI = "/toDo";

	// Create test
	@Test
	void createTest() throws Exception {
		ToDoDto testDTO = mapToDTO(new ToDo("title 4","task 4"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isCreated();

		ToDoDto testSavedDTO = mapToDTO(new ToDo("title 4", "task 4"));
		testSavedDTO.setId(13L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);


	}
	// Update test
	@Test
		void updateTest() throws Exception {
		ToDoDto testDTO = mapToDTO(new ToDo("title 4", "task 4"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "/update/1").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isAccepted();

		ToDoDto testSavedDTO = mapToDTO(new ToDo("title 4", "task 4"));
		testSavedDTO.setId(1L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	
				}
	// Read test
	@Test
	void ReadOne() throws Exception {
		ToDoDto testDTO = mapToDTO(new ToDo("title 1","task 1"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = get(URI + "/read/1").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

		ResultMatcher checkStatus = status().isOk();

		ToDoDto testSavedDTO = mapToDTO(new ToDo("title 1","task 1"));
		testSavedDTO.setId(1L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);


	}
	
	// Read All test
		@Test
		void ReadAll() throws Exception {
			ToDoDto testDTO = mapToDTO(new ToDo("title 1" , "task 1"));
			ToDoDto testDTO2 = mapToDTO(new ToDo("title 2","task 2"));
			ToDoDto testDTO3 = mapToDTO(new ToDo("title 3", "task 3"));
			List<ToDoDto> listDTO = new ArrayList<>();
			listDTO.add(testDTO);
			listDTO.add(testDTO2);
			listDTO.add(testDTO3);

			String testDTOAsJSON = this.jsonifier.writeValueAsString(listDTO);

			RequestBuilder request = get(URI + "/read").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);

			ResultMatcher checkStatus = status().isOk();

			ToDoDto testSavedDTO = mapToDTO(new ToDo("title 1","task 1"));
			ToDoDto testSavedDTO2 = mapToDTO(new ToDo("title 2","task 2"));
			ToDoDto testSavedDTO3 = mapToDTO(new ToDo("title 3","task 3"));
			List<ToDoDto> listSavedDTO = new ArrayList<>();
			testSavedDTO.setId(1L);
			testSavedDTO2.setId(2L);
			testSavedDTO3.setId(3L);
			listSavedDTO.add(testSavedDTO);
			listSavedDTO.add(testSavedDTO2);
			listSavedDTO.add(testSavedDTO3);
			String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(listSavedDTO);

			ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);


		}
		
		// Delete test
		@Test
		void deleteTest() throws Exception {

			RequestBuilder request = delete(URI + "/delete/1").contentType(MediaType.APPLICATION_JSON);

			ResultMatcher checkStatus = status().isNoContent();

			this.mvc.perform(request).andExpect(checkStatus);


		}



	


	
}