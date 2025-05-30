package com.example.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
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

	@Async("emailExecutor")
	public void sendMail(String to, String subject, String body) {
		try {
			log.info("Starting async email sending to: {}", to);
			var message = new SimpleMailMessage();
			message.setFrom(from);
			message.setSubject(subject);
			message.setTo(to);
			message.setText(body);
			javaMailSender.send(message);
			log.info("Successfully sent email to: {}", to);
		} catch (Exception e) {
			log.error("Failed to send email to: {}. Error: {}", to, e.getMessage());
			log.debug("Detailed error for email sending failure:", e);
		}
	}

	@Async("emailExecutor")
	public void sendMailResetPassword(String role, String name, String email, String password, String resetCode) {
		log.info("Preparing reset password email for {} user: {}", role, email);
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
