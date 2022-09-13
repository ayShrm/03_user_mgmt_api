package com.ayush.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class AccountActDto {
	
	@NotNull(message = "Email should not be null")
	@NotEmpty(message = "Email should not be empty")
	@Pattern(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@(.+)$)")
	@Email
	private String activateEmail;

	@NotNull
	@NotEmpty(message = "Please enter temporary password")
	private String tempPwd;

	@NotNull
	@NotEmpty(message = "New Password cannot be empty")
	private String newPwd;

	@NotNull
	@NotEmpty(message = "Enter the new password again")
	private String cnfmPwd;

}
