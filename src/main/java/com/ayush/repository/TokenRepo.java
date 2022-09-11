package com.ayush.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ayush.entity.Token;
import com.ayush.entity.UserDetails;

public interface TokenRepo extends JpaRepository<Token, String> {

	Optional<Token> findUserByToken(String token);

	@Query("SELECT t FROM Token t WHERE t.userDetails = ?1")
	Token findToken(UserDetails user);
}
