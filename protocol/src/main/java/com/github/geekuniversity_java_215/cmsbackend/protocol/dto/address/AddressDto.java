package com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address;

import com.github.geekuniversity_java_215.cmsbackend.protocol.dto._base.AbstractDto;
import lombok.Data;

@Data
public class AddressDto extends AbstractDto {

    protected Long id;
    private String city;
    private String street;
    private Integer house;
    private Integer building;
    private Integer flat;
    private Double longitude;
    private Double latitude;
}
