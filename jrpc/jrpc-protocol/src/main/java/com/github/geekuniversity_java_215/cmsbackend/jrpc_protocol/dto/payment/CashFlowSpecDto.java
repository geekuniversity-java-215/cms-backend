package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.Data;

import java.time.Instant;

@Data
public class CashFlowSpecDto extends AbstractSpecDto {

    private Long id;
    private UserDto user;
    private Instant startDate;
    private Instant endDate;
    //private Instant dateSuccess;
    private Boolean successful;
}
