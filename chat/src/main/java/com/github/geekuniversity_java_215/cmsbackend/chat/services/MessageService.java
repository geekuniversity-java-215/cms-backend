package com.github.geekuniversity_java_215.cmsbackend.chat.services;

import com.github.geekuniversity_java_215.cmsbackend.chat.entities.ChatMessage;
import com.github.geekuniversity_java_215.cmsbackend.chat.repositories.MessageRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService extends BaseRepoAccessService<ChatMessage> {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        super(messageRepository);
        this.messageRepository = messageRepository;
    }

    public List<ChatMessage> findAll() {
        return messageRepository.findAll();
    }

    public Optional<ChatMessage> findById(Long id) {
        return messageRepository.findById(id);
    }

    public List<ChatMessage> findByOrderId(Long id) {
        return messageRepository.findByOrderId(id);
    }

    public List<ChatMessage> findByOrderAndSender(Order order, User user) {
        return messageRepository.findByOrderAndUser(order, user);
    }
}
