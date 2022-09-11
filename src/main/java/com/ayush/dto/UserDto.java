package com.ayush.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

//	@NotNull
//	@NotEmpty
	private String fullName;

//	@NotNull(message = "Email should not be null")
//	@NotEmpty(message = "Email should not be empty")
//	@Pattern(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@(.+)$)")
	@Email
	private String userEmail;

//	@NotNull
//	@NotEmpty
//	@Pattern(message = "Invalid Phone No.", regexp = "(^$|[0-9]{10})")
	private String mobile;

//	@NotNull
//	@NotEmpty(message = "DOB should not be empty")
	private String dob;

//	@NotNull
//	@NotEmpty(message = "Please select the Gender")
	private String gender;

//	@NotNull
	private Long ssn;

//	@NotNull
//	@NotEmpty(message = "Password cannot be empty")
	private String password;

//	@NotNull
//	@NotEmpty(message = "Please enter temporary password")
	private String tempPwd;

//	@NotNull
//	@NotEmpty(message = "New Password cannot be empty")
	private String newPwd;

//	@NotNull
//	@NotEmpty(message = "Enter the new password again")
	private String cnfmPwd;
}
