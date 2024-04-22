package com.feuji.timesheetentryservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailSender {
	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleEmail(String toEmail, String subject, String body) {
		

		try {
			log.info("Email sent to this email address: " + toEmail);
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("kotipalli005@gmail.com");
			message.setTo(toEmail);
			message.setText(body);
			message.setSubject(subject);
			mailSender.send(message);
			log.info("Email sent to this email address: " + toEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}

}
}
