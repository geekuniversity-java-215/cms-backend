package com.github.geekuniversity_java_215.cmsbackend.payment.dto;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends AbstractDto {

    private User user;
    private BigDecimal amount;
    private String payPalEmail;

    private String currencyCodeType;
    private String typeOperation;

    private Date dateCreate;
    private Date dateSuccess;


}
