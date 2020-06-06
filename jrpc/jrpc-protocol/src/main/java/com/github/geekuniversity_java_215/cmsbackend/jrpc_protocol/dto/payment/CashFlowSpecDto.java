package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment;

import lombok.Data;

import java.time.Instant;

@Data
public class CashFlowSpecDto {

    private Long user_id;
    private Instant startDate;
    private Instant endDate;
}
