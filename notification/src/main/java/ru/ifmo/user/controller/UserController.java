package ru.ifmo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.user.socket.NotificationWebSocket;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@EnableKafka
public class UserController {
    private final NotificationWebSocket notificationWebSocket;
    @KafkaListener(topics = "notification")
    public void sendNotification(@Payload String message, @Header(KafkaHeaders.RECEIVED_KEY) String author) throws IOException {
        notificationWebSocket.sendNotification(author, message);
    }

}
