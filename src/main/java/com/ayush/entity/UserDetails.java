package com.ayush.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {

	@Id
	@GeneratedValue
	@Column(name = "S.NO.")
	private Integer userId;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;

	@Column(name = "PHONE_NO.", unique = true)
	private String mobile;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "SSN", unique = true)
	private Long ssn;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "STATUS", columnDefinition = "tinyint(1) default false")
	private boolean active;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@CreationTimestamp
	@Column(name = "CREATE_DATE", updatable = false)
	private LocalDate createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DATE", insertable = false)
	private LocalDate updateDate;

}
