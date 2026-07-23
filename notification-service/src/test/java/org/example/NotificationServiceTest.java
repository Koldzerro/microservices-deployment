package org.example;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import org.example.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotificationServiceTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("test@example.com", "test"))
            .withPerMethodLifecycle(true);

    @Autowired
    private NotificationService notificationService;

    @Test
    void sendCreatedNotification_shouldSendEmail() throws Exception {
        // Act
        notificationService.sendCreatedNotification("user@example.com");

        // Assert
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Добро пожаловать!", messages[0].getSubject());
        assertTrue(messages[0].getContent().toString()
                .contains("Ваш аккаунт на сайте был успешно создан"));
    }

    @Test
    void sendDeletedNotification_shouldSendEmail() throws Exception {
        // Act
        notificationService.sendDeletedNotification("user@example.com");

        // Assert
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Аккаунт удалён", messages[0].getSubject());
        assertTrue(messages[0].getContent().toString()
                .contains("Ваш аккаунт был удалён"));
    }

    @Test
    void sendCustomNotification_shouldSendEmail() throws Exception {
        // Act
        notificationService.sendCustomNotification("user@example.com", "Тест", "Тестовое сообщение");

        // Assert
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Тест", messages[0].getSubject());
        assertTrue(messages[0].getContent().toString()
                .contains("Тестовое сообщение"));
    }
}