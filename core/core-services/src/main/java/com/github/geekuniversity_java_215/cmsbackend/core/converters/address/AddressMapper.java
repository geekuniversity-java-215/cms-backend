package com.github.geekuniversity_java_215.cmsbackend.core.converters.address;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class AddressMapper extends AbstractMapper<Address, AddressDto> {

    public abstract AddressDto toDto(Address order);

    public abstract Address toEntity(AddressDto orderDto);

    @AfterMapping
    void afterMapping(AddressDto source, @MappingTarget Address target) {
        idMap(source, target);
    }

}
