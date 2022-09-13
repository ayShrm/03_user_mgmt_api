package com.ayush.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LoginDto {
	

	@NotNull(message = "Email should not be null")
	@NotEmpty(message = "Email should not be empty")
	@Pattern(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@(.+)$)")
	@Email
	private String loginEmail;
	
	@NotNull
	@NotEmpty(message = "Password cannot be empty")
	private String password;

}
