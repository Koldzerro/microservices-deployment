package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendCreatedNotification(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Добро пожаловать!");
        message.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        mailSender.send(message);
        System.out.println("Отправлено письмо о создании на: " + toEmail);
    }

    public void sendDeletedNotification(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Аккаунт удалён");
        message.setText("Здравствуйте! Ваш аккаунт был удалён.");
        mailSender.send(message);
        System.out.println("Отправлено письмо об удалении на: " + toEmail);
    }

    public void sendCustomNotification(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Отправлено письмо на: " + toEmail);
    }
}