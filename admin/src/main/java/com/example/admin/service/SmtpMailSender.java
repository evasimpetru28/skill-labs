package com.example.admin.service;

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
	@Value("${skilllab.host.url}")
	private String adminHostLink;
	@Value("${student.host.url}")
	private String studentHostLink;

	public void sendMail(String to, String subject, String body) {
		var message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSubject(subject);
		message.setTo(to);
		message.setText(body);
		javaMailSender.send(message);
	}

	public void sendMailResetPassword(Boolean isStudent, String name, String email, String password, String resetCode) {
		var subject = "Skill Labs - Reset password";
		var body = "Hello " + name +
				   ",\n\nHere are your credentials for Skill Labs:\n\n" +
				   "User: " + email + "\n" +
				   "Password: " + password + "\n\n" +
				   "Please reset your password by clicking the link below:\n\n" + getHostLink(isStudent, resetCode);

		sendMail(email, subject, body);
	}

	private String getHostLink(Boolean isStudent, String resetCode) {
		return (isStudent ? studentHostLink : adminHostLink) + "/reset-password/" + resetCode;
	}

}
