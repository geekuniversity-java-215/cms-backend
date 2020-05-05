package com.github.geekuniversity_java_215.cmsbackend.chat.controllers;

import com.github.geekuniversity_java_215.cmsbackend.chat.protocol.ChatMessageDto;
import com.github.geekuniversity_java_215.cmsbackend.chat.protocol.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Map<String, Object> attributes = headerAccessor.getSessionAttributes();

        if (!attributes.containsKey("login") ||
            !attributes.containsKey("order")) {
            throw new RuntimeException("Required headers 'username' || 'order' not found");
        }

        String username = (String)attributes.get("username");
        Long orderId = (Long)attributes.get("order");

        if(username != null) {
            log.info("User Disconnected : " + username);

            ChatMessageDto messageDto = new ChatMessageDto();
            messageDto.setUserName(username);
            messageDto.setType(MessageType.LEAVE);
            messageDto.setOrderId(orderId);

            messagingTemplate.convertAndSend("/topic/" + orderId, messageDto);
        }
    }
}