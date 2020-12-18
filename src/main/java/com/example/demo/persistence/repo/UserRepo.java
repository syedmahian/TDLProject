package com.example.demo.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.persistence.domain.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	
	
	// find all by title
		// JPQL
		@Query(value = "SELECT * FROM USER WHERE FULLNAME =?1", nativeQuery = true)
		List<User> findByFullName(String fullName);
}