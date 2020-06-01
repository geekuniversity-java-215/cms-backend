package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment;


import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@EqualsAndHashCode(callSuper = true)
public class CashFlowDto extends AbstractDto {

    private UserDto user;
    private BigDecimal amount;
    private String payPalEmail;

    private CurrencyCode currencyCodeType;
    private String typeOperation;

    private Instant dateSuccess;
}
