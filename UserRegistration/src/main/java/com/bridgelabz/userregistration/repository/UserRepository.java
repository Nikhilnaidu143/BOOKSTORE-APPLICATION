package com.bridgelabz.userregistration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.userregistration.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM user_details WHERE email = :email", nativeQuery = true)
	List<User> findUserByEmail(String email);
}