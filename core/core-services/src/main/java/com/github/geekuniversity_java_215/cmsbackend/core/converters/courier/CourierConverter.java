package com.github.geekuniversity_java_215.cmsbackend.core.converters.courier;


import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import org.springframework.beans.factory.annotation.Autowired;

public class CourierConverter extends AbstractConverter<Courier, CourierDto, Void> {

    @Autowired
    public CourierConverter(CourierMapper courierMapper) {
        this.entityMapper = courierMapper;

        this.entityClass = Courier.class;
        this.dtoClass = CourierDto.class;
        this.specClass = Void.class;
    }


    @Override
    protected void validate(Courier courier) {
        super.validate(courier);

        // ... custom validation
    }
}
