package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDto extends AbstractDto {

    private UserDto user;
    private String clientSpecificData;
}
