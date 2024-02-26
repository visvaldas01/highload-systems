package ru.ifmo.user.socket;

import org.springframework.web.socket.*;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationWebSocket implements WebSocketHandler {
    private HashMap<String, List<WebSocketSession>> authorSubscription = new HashMap<>();
    private List<WebSocketSession> sessions = new ArrayList<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (!authorSubscription.containsKey((String)message.getPayload()))
            authorSubscription.put((String)message.getPayload(), new ArrayList<>());
        if (!authorSubscription.get(message.getPayload().toString()).contains(session))
            authorSubscription.get(message.getPayload().toString()).add(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        authorSubscription.forEach(((s, webSocketSessions) -> webSocketSessions.remove(session)));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendNotification(String author, String message)
    {
        if (authorSubscription.containsKey(author)) {
            authorSubscription.get(author).forEach((webSocketSession -> {
                try {
                    webSocketSession.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }
}
