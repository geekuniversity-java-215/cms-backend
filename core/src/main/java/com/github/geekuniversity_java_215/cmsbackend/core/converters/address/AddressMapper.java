package com.github.geekuniversity_java_215.cmsbackend.core.converters.address;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.order.OrderDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class AddressMapper extends AbstractMapper {

    public abstract AddressDto toDto(Address order);

    public abstract Address toEntity(AddressDto orderDto);

    @AfterMapping
    void afterMapping(OrderDto source, @MappingTarget Order target) {
        idMap(source, target);
    }

}
