package com.github.geekuniversity_java_215.cmsbackend.core.converters.client;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class ClientMapper extends AbstractMapper<Client, ClientDto> {

    @Autowired
    private ClientService clientService;

    public abstract ClientDto toDto(Client client);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    public abstract Client toEntity(ClientDto clientDto);

    @AfterMapping
    void afterMapping(ClientDto source, @MappingTarget Client target) {
        idMap(source, target);

        Client client = clientService.findById(source.getId())
            .orElseThrow(() -> new RuntimeException("Client not found"));
    }

}
