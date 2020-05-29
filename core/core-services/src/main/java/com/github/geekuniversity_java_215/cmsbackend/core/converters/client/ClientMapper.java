package com.github.geekuniversity_java_215.cmsbackend.core.converters.client;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class, UserMapper.class})
public abstract class ClientMapper extends AbstractMapper<Client, ClientDto> {

    @Autowired
    private ClientService clientService;

    @PostConstruct
    private void postConstruct() {
        super.setBaseRepoAccessService(clientService);
    }

    public abstract ClientDto toDto(Client client);

    //@Mapping(target = "user", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    public abstract Client toEntity(ClientDto clientDto);

    @AfterMapping
    void afterMapping(ClientDto source, @MappingTarget Client target) {

        //idMap(source, target);
        merge(source, target);
    }

    public static class ClientConstructor extends Constructor<Client, ClientDto> {
        @Override
        public Client create(ClientDto dto, Client entity) {
            return new Client(
                entity.getUser(),
                dto.getClientSpecificData()
            );
        }
    }

}
