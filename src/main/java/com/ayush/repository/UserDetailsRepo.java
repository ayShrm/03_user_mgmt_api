package com.ayush.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ayush.entity.UserDetails;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {

	@Query("SELECT u FROM UserDetails u WHERE u.emailId = ?1")
	UserDetails findByEmail(String emailId);
}
