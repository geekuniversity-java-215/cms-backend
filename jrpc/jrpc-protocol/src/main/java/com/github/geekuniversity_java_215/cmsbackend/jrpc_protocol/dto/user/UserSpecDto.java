package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserSpecDto {

    public enum OrderBy {ASC,DESC}

    // null - выдать сразу все заказы, иначе по сколько отдавать
    private Integer limit;

    // принадлежность курьеру
    private CourierDto courier;

    // принадлежность клиенту
    private ClientDto client;
}
