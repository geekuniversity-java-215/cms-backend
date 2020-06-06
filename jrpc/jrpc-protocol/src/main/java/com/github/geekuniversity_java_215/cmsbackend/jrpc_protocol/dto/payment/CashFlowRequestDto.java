package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = true)
public class CashFlowRequestDto extends AbstractDto {

    private BigDecimal amount;
    private String payPalEmail;

}
