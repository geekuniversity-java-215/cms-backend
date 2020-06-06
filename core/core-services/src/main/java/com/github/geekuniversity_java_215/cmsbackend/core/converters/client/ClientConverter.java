package com.github.geekuniversity_java_215.cmsbackend.core.converters.client;


import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends AbstractConverter<Client, ClientDto, AbstractSpecDto> {

    @Autowired
    public ClientConverter(ClientMapper clientMapper) {
        this.entityMapper = clientMapper;

        this.entityClass = Client.class;
        this.dtoClass = ClientDto.class;
        this.specClass = AbstractSpecDto.class;
    }


    @Override
    protected void validate(Client client) {
        super.validate(client);

        // ... custom validation
    }
}
