package com.github.geekuniversity_java_215.cmsbackend.chat.repositories;

import com.github.geekuniversity_java_215.cmsbackend.chat.entities.ChatMessage;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CustomRepository<ChatMessage, Long> {
    List<ChatMessage> findByOrderId(Long id);
    List<ChatMessage> findByOrderAndUser(Order order, User user);
}
