package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourierDto extends AbstractDto {
    private String courierSpecificData;
}
