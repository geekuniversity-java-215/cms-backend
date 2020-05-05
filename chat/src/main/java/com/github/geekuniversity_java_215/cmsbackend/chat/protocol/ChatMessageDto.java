package com.github.geekuniversity_java_215.cmsbackend.chat.protocol;

import com.github.geekuniversity_java_215.cmsbackend.chat.protocol.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class ChatMessageDto implements Serializable {
    private String userName;
    private String content;
    private MessageType type;
    private Long orderId;
    private String time;
}
