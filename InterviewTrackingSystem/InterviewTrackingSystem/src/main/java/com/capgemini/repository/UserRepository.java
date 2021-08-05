package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.entities.User;


public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value="Select u from User u where u.username = :username And u.password = :password")
	public User validateUser(@Param("username") String username ,@Param("password") String password);

}
