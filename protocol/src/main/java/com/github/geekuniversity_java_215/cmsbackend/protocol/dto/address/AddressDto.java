package com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address;

import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.base.AbstractDto;
import lombok.Data;

@Data
public class AddressDto extends AbstractDto {

    protected Long id;
    private String city;
    private String street;
    private int house;
    private int building;
    private int front_door;
    private int flat;
    private String longitude;
    private String latitude;
}
