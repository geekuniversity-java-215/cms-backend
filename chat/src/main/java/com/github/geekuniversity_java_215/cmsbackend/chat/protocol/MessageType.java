package com.github.geekuniversity_java_215.cmsbackend.chat.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum MessageType {
    CHAT(0, "Сообщение"),
    JOIN(1, "Присоединиться"),
    LEAVE(2, "Покинуть");

    @ToString.Exclude
    private final int id;

    @ToString.Include
    private final String name;
}
