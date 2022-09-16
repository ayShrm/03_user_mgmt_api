package com.ayush.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendMail(String toEmail, String subject, String body) {
		boolean isMailSent = false;
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage);

			mailMessage.setTo(toEmail);
			mailMessage.setSubject(subject);
			mailMessage.setText(body, true);

			mailMessage.setFrom("support@demo.com");
			javaMailSender.send(mimeMessage);
			
			isMailSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMailSent;
	}

}
