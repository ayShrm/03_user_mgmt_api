package com.ayush.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	@NotNull(message = "Email should not be null")
	@NotEmpty(message = "Email should not be empty")
	@Pattern(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@(.+)$)")
	@Email
	private String userEmail;
	
	@NotNull
	@NotEmpty
	private String fullName;

	@NotNull
	@NotEmpty
	@Pattern(message = "Invalid Phone No.", regexp = "(^$|[0-9]{10})")
	private Long mobile;

	@NotNull
	@NotEmpty(message = "DOB should not be empty")
	private String dob;

	@NotNull
	@NotEmpty(message = "Please select the Gender")
	private String gender;

	@NotNull
	private Long ssn;
}
