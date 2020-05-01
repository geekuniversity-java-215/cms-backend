package com.github.geekuniversity_java_215.cmsbackend.core.converters.address;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;

import javax.validation.ValidationException;

public class AddressConverter extends AbstractConverter<Address, AddressDto, Void> {

    @Autowired
    public AddressConverter(AddressMapper addressMapper) {
        this.entityMapper = addressMapper;

        this.entityClass = Address.class;
        this.dtoClass = AddressDto.class;
        this.specClass = Void.class;
    }


    @Override
    protected void validate(Address address) {
        super.validate(address);

        // ... custom validation
    }
}
