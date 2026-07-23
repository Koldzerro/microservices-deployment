package org.example.kafka;

import org.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserEventConsumer {

    @Autowired
    private NotificationService notificationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.topic.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserEvent(String message) {
        try {
            JsonNode json = objectMapper.readTree(message);
            String operation = json.get("operation").asText();
            String email = json.get("email").asText();

            if ("CREATED".equals(operation)) {
                notificationService.sendCreatedNotification(email);
            } else if ("DELETED".equals(operation)) {
                notificationService.sendDeletedNotification(email);
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки сообщения: " + e.getMessage());
        }
    }
}