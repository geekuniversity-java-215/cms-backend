package com.github.geekuniversity_java_215.cmsbackend.core.converters.courier;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class, UserMapper.class})
public abstract class CourierMapper extends AbstractMapper<Courier, CourierDto> {

    @Autowired
    private CourierService courierService;

    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = courierService;
        constructor = new EntityConstructor();
    }

    //@Mapping(source = "courier.id", target = "courierId")
    public abstract CourierDto toDto(Courier courier);

    @Mapping(target = "orderList", ignore = true)
    public abstract Courier toEntity(CourierDto courierDto);

    @AfterMapping
    void afterMapping(CourierDto source, @MappingTarget Courier target) {
        merge(source, target);
    }

    protected class EntityConstructor extends Constructor<Courier, CourierDto> {
        @Override
        public Courier create(CourierDto dto, Courier entity) {
            return new Courier(
                entity.getUser(),
                dto.getCourierSpecificData()
            );
        }
    }

}
