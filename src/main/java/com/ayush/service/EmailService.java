package com.ayush.service;

public interface EmailService {

	public boolean sendMail(String toEmail, String subject, String message);
}
