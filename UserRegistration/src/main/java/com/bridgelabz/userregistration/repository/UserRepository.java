package com.bridgelabz.userregistration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.userregistration.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// Find by email
	@Query(value = "SELECT * FROM user_details WHERE email = :email", nativeQuery = true)
	List<User> findUserByEmail(String email);

	// Find all whose expiry date is less than or equal to 30 days.
	@Query(value = "SELECT * FROM user_details WHERE now() > expiry_date - interval 30 DAY", nativeQuery = true)
	List<User> findUserByExpiryIn30Days();

}