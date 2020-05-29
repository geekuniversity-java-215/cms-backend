package com.github.geekuniversity_java_215.cmsbackend.core.converters.address;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AddressService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class AddressMapper extends AbstractMapper<Address, AddressDto> {

    @Autowired
    private AddressService addressService;

    @PostConstruct
    private void postConstruct() {
        super.setBaseRepoAccessService(addressService);
    }

    public abstract AddressDto toDto(Address order);

    public abstract Address toEntity(AddressDto orderDto);

    @AfterMapping
    Address afterMapping(AddressDto source, @MappingTarget Address target) {
        return merge(source, target);
    }

    public static class AddressConstructor extends Constructor<Address, AddressDto> {
        @Override
        public Address create(AddressDto dto, Address entity) {
            return new Address(
                dto.getCity(),
                dto.getStreet(),
                dto.getHouse(),
                dto.getBuilding(),
                dto.getFlat());
        }
    }

}
