package com.github.geekuniversity_java_215.cmsbackend.core.converters.address;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AddressService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.address.AddressDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(config = AbstractMapper.class)
public abstract class AddressMapper extends AbstractMapper<Address, AddressDto> {

    @Autowired
    private AddressService addressService;

    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = addressService;
        constructor = new EntityConstructor();
    }

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    public abstract AddressDto toDto(Address order);

    public abstract Address toEntity(AddressDto orderDto);

    @AfterMapping
    Address afterMapping(AddressDto source, @MappingTarget Address target) {
        return merge(source, target);
    }

    protected class EntityConstructor extends Constructor<Address, AddressDto> {
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
