package com.ayush.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "Tokens")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "token_id")
	private long tokenid;

	@Column(name = "confirmation_token")
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@OneToOne(targetEntity = UserDetails.class, fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(nullable = false, name = "user_id")
	private UserDetails userDetails;

	public Token() {
	}

	public Token(UserDetails userDetails) {
		this.userDetails = userDetails;
		createdDate = new Date();
		token = UUID.randomUUID().toString();
	}

}
