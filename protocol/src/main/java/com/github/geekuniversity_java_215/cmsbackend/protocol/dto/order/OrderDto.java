package com.github.geekuniversity_java_215.cmsbackend.protocol.dto.order;

import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.base.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends AbstractDto {

    private Long id;
    private Long customerId;
    private Long courierId;

    private AddressDto from;
    private AddressDto to;

    private String Status;
}
