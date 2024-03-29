package com.example.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailSender {
	final JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String from;
	@Value("${student.host.url}")
	private String hostLink;

	public void sendMail(String to, String subject, String body) {
		var message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSubject(subject);
		message.setTo(to);
		message.setText(body);
		javaMailSender.send(message);
	}

	public void sendMailResetPassword(String name, String email, String password, String resetCode) {
		var subject = "Reset password";
		var body = "Hello " + name +
				   ",\n\nHere are your credentials for Skill Labs:\n\n" +
				   "User: " + email + "\n" +
				   "Password: " + password + "\n\n" +
				   "Please reset your password by clicking the link below:\n\n" + getRandomLink(resetCode);

		sendMail(email, subject, body);
	}

	private String getRandomLink(String resetCode) {
		return hostLink + "/reset-password/" + resetCode;
	}

}
