package com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ClientConfigurationMapper {

    public abstract JrpcClientProperties toProperties(JrpcClientPropertiesFile fileProperties);

    public abstract JrpcClientProperties deepCopy(JrpcClientProperties properties);

//    @AfterMapping
//    void afterMapping(AddressDto source, @MappingTarget Address target) {
//        idMap(source, target);
//    }

}
