package org.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.user-events}")
    private String topic;

    public void sendUserCreatedEvent(String email) {
        String message = "{\"operation\":\"CREATED\",\"email\":\"" + email + "\"}";
        kafkaTemplate.send(topic, message);
        System.out.println("Отправлено событие создания: " + message);
    }

    public void sendUserDeletedEvent(String email) {
        String message = "{\"operation\":\"DELETED\",\"email\":\"" + email + "\"}";
        kafkaTemplate.send(topic, message);
        System.out.println("Отправлено событие удаления: " + message);
    }
}