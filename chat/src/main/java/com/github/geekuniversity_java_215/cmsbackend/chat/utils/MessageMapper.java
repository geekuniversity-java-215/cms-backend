package com.github.geekuniversity_java_215.cmsbackend.chat.utils;

import com.github.geekuniversity_java_215.cmsbackend.chat.entities.ChatMessage;
import com.github.geekuniversity_java_215.cmsbackend.chat.protocol.ChatMessageDto;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MessageMapper {
    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "username", expression = "java(message.getUser().getFullName())")
    @Mapping(target = "orderId", source = "message.order.id")
//    @Mapping(target = "time", source = "java(new SimpleDateFormat(\"HH:mm\").format(message.getCreated()))")
    ChatMessageDto toChatMessageDto(ChatMessage message);   //fromChatMessage

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "enabled", ignore = true)
    ChatMessage toChatMessage(ChatMessageDto chatMessageDto, User user, Order order);

    default List<ChatMessageDto> fromMessageList(List<ChatMessage> chatMessages) {
        return chatMessages.stream().map(this::toChatMessageDto).collect(Collectors.toList());
    }
}
