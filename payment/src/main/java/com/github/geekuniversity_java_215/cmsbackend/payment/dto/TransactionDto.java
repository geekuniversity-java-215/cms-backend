package com.github.geekuniversity_java_215.cmsbackend.payment.dto;

import com.github.geekuniversity_java_215.cmsbackend.protocol.dto._base.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends AbstractDto {

    private Long userId;
    private BigDecimal amount;

}
