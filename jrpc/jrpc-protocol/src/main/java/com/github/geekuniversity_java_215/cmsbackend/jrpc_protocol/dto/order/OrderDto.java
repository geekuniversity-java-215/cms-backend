package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends AbstractDto {

    private ClientDto client;
    private CourierDto courier;

    private AddressDto from;
    private AddressDto to;

    private OrderStatusDto status;

    // moar info ...
}
