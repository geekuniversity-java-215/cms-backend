package com.github.geekuniversity_java_215.cmsbackend.chat.basic;

import com.github.geekuniversity_java_215.cmsbackend.chat.ChatApplication;
import com.github.geekuniversity_java_215.cmsbackend.chat.entities.ChatMessage;
import com.github.geekuniversity_java_215.cmsbackend.chat.services.MessageService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.*;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.utils.Junit5Extension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootTest(classes = {ChatApplication.class})
@ExtendWith({Junit5Extension.class})
@Slf4j
@SuppressWarnings({"OptionalGetWithoutIsPresent"})
public class ChatTests {
    @Autowired
    ApplicationContext context;


    @Autowired
    private MessageService messageService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private OrderService orderService;

    @Test
    void basicTests() throws Exception {
        
        Courier courier = courierService.findById(1L).get();
        Client client = clientService.findById(1L).get();
        Order order = orderService.findById(1L).get();

        // TESTING CHAT MESSAGE
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(courier.getUser());
        chatMessage.setOrder(order);
        messageService.save(chatMessage);
        log.info("message.id: {}", chatMessage.getId());

        List<ChatMessage> chatMessages = messageService.findByOrderAndSender(order, courier.getUser());
        log.info("messages.size(): {}", chatMessages.size());
    }
}
