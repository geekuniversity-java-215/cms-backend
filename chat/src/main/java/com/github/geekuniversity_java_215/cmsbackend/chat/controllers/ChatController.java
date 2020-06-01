package com.github.geekuniversity_java_215.cmsbackend.chat.controllers;

import com.github.geekuniversity_java_215.cmsbackend.chat.entities.ChatMessage;
import com.github.geekuniversity_java_215.cmsbackend.chat.services.MessageService;
import com.github.geekuniversity_java_215.cmsbackend.chat.utils.MessageMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/chat")
public class ChatController {
    private final MessageService messageService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ChatController(MessageService messageService, UserService userService, OrderService orderService) {
        this.messageService = messageService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public String beginChat(Model model, @PathVariable Long orderId) {

        User user = userService.getCurrentUser();
        model.addAttribute("username", user.getFullName());
        model.addAttribute("orderId", orderId);
        return "chat";
    }

    @GetMapping("/history/{orderId}")
    public String viewHistory(Model model, @PathVariable Long orderId) {

        User user = userService.getCurrentUser();

        Order order = orderService.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order " + orderId + " not found"));

        List<ChatMessage> chatMessages = messageService.findByOrderAndSender(order, user);
        model.addAttribute("messages", MessageMapper.MAPPER.fromMessageList(chatMessages));

        return "history";
    }
}
