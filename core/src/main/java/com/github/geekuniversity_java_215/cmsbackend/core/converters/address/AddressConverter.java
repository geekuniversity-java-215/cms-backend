package com.github.geekuniversity_java_215.cmsbackend.core.converters.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.address.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;

import javax.validation.ValidationException;

public class AddressConverter extends AbstractConverter<Address> {

    private final AddressMapper addressMapper;

    @Autowired
    public AddressConverter(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }




    // Json => Dto => Entity
    public Address toEntity(JsonNode params)  {
        try {
            AddressDto dto = objectMapper.treeToValue(params, AddressDto.class);
            Address result = addressMapper.toEntity(dto);
            validate(result);
            return result;
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ParseException(0, "OrderDto parse error", e);
        }
    }


    // Entity => Dto => Json
    public JsonNode toDtoJson(Address address) {
        try {
            AddressDto dto = addressMapper.toDto(address);
            return objectMapper.valueToTree(dto);
        }
        catch (Exception e) {
            throw new ParseException(0, "OrderToDtoJson convert error", e);
        }
    }

    @Override
    protected void validate(Address address) {
        super.validate(address);

        // ... custom validation
    }
}
