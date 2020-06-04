package com.github.geekuniversity_java_215.cmsbackend.chat.controllers;

import com.github.geekuniversity_java_215.cmsbackend.chat.entities.ChatMessage;
import com.github.geekuniversity_java_215.cmsbackend.chat.protocol.ChatMessageDto;
import com.github.geekuniversity_java_215.cmsbackend.chat.services.MessageService;
import com.github.geekuniversity_java_215.cmsbackend.chat.utils.MessageMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Controller
public class ChatMessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ChatMessageController(SimpMessagingTemplate template,
                                 MessageService messageService,
                                 UserService userService,
                                 OrderService orderService) {
        this.template = template;
        this.messageService = messageService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @MessageMapping("/chat.sendMessage/{orderId}")
    public void sendMessage(@Payload ChatMessageDto messageLite,
                            @DestinationVariable Long orderId) {

        User user = userService.getCurrent();

        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order " + orderId + " not found"));

        ChatMessage chatMessage = messageService.save(MessageMapper.MAPPER.toChatMessage(messageLite, user, order));
        template.convertAndSend("/topic/" + orderId, MessageMapper.MAPPER.toChatMessageDto(chatMessage));
    }

    @MessageMapping("/chat.addUser/{orderId}")
    public void addUser(@Payload ChatMessageDto messageLite,
                        @DestinationVariable Long orderId,
                        SimpMessageHeaderAccessor headerAccessor) {

        User user = userService.getCurrent();

        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order " + orderId + " not found"));

        Assert.isTrue(headerAccessor!= null &&
            headerAccessor.getSessionAttributes() != null , "headerAccessor == null || " +
            "headerAccessor.getSessionAttributes == null");

        headerAccessor.getSessionAttributes().put("username", user.getFullName());
        headerAccessor.getSessionAttributes().put("order", orderId);

        ChatMessage chatMessage = messageService.save(MessageMapper.MAPPER.toChatMessage(messageLite, user, order));
        template.convertAndSend("/topic/" + orderId, MessageMapper.MAPPER.toChatMessageDto(chatMessage));
    }
}
