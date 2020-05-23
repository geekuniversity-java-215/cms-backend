package com.github.geekuniversity_java_215.cmsbackend.core.converters.courier;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class CourierMapper extends AbstractMapper<Courier, CourierDto> {

    @Autowired
    private CourierService courierService;



    //@Mapping(source = "courier.id", target = "courierId")
    public abstract CourierDto toDto(Courier courier);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    public abstract Courier toEntity(CourierDto courierDto);

    @AfterMapping
    void afterMapping(CourierDto source, @MappingTarget Courier target) {
        idMap(source, target);

        Courier courier = courierService.findById(source.getId())
            .orElseThrow(() -> new RuntimeException("Courier not found"));
    }

}
