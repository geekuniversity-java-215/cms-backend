package com.github.geekuniversity_java_215.cmsbackend.chat.entities;

import com.github.geekuniversity_java_215.cmsbackend.chat.protocol.MessageType;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "chat_message")
@Data
@EqualsAndHashCode(callSuper = true)
//@NoArgsConstructor
public class ChatMessage extends AbstractEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private String text;

    @Column
    private MessageType type;
}
