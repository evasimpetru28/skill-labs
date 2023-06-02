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
	@Value("${professor.host.url}")
	private String professorHostLink;

	public void sendMail(String to, String subject, String body) {
		var message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSubject(subject);
		message.setTo(to);
		message.setText(body);
		javaMailSender.send(message);
	}

	public void sendMailResetPassword(String role, String name, String email, String password, String resetCode) {
		var subject = "Skill Labs - Reset password";
		var body = "Hello " + name +
				   ",\n\nHere are your credentials for Skill Labs:\n\n" +
				   "User: " + email + "\n" +
				   "Password: " + password + "\n\n" +
				   "Please reset your password by clicking the link below:\n\n" + getHostLink(role, resetCode);

		sendMail(email, subject, body);
	}

	private String getHostLink(String role, String resetCode) {
		String link;
		switch (role) {
			case "STUDENT" -> link = studentHostLink;
			case "SUPERUSER" -> link = professorHostLink;
			default -> link = adminHostLink;
		}
		return (link + "/reset-password/" + resetCode);
	}

}
