  
package com.example.demo.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.persistence.domain.ToDo;

@Repository
public interface ToDoRepo extends JpaRepository<ToDo, Long> {

	// it allows us to implement
	// create
	// read
	// update
	// delete

	// custom sql statements e.g. find by make or model .........

	// find all by title
	// JPQL
	@Query(value = "SELECT * FROM TODO WHERE TITLE =?1", nativeQuery = true)
	List<ToDo> findByTitle(String title);

}