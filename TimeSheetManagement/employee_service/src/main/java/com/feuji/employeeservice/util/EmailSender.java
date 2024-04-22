package com.feuji.employeeservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@DependsOn("mailSender") 
public class EmailSender {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("kotipalli005@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            log.info("Mail sender object: {}", mailSender);
            mailSender.send(message);
            log.info("Email sent to: " + toEmail);
        } catch (Exception e) {
            log.error("Error sending email", e);
        }
    }
}