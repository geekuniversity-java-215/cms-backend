package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.address.AddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends AbstractDto {

    private Long clientId;
    private Long courierId;

    private AddressDto from;
    private AddressDto to;

    private Integer status;
}
