package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class PaymentRequestDto {
    private BigDecimal amount;
}
