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
	private String link;

	public void sendMail(String to, String subject, String body) {
		var message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSubject(subject);
		message.setTo(to);
		message.setText(body);
		javaMailSender.send(message);
	}

	public void sendMailResetPassword(String email, String resetCode) {
		var subject = "Reset password";
		var body = "Please reset your password by clicking the link below:\n\n" + getRandomLink(resetCode);

		sendMail(email, subject, body);
	}

	private String getRandomLink(String resetCode) {
		return link + "/reset-password/" + resetCode;
	}

}
