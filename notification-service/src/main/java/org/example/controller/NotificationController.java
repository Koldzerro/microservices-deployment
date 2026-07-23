package org.example.controller;

import org.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String text) {
        notificationService.sendCustomNotification(email, subject, text);
        return "Письмо отправлено на: " + email;
    }
}