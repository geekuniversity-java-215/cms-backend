package com.github.geekuniversity_java_215.cmsbackend.core.converters.user;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class UserMapper extends AbstractMapper<User, UserDto> {

    @Autowired
    private UserService userService;

    @PostConstruct
    private void postConstruct() {
        super.setBaseRepoAccessService(userService);
    }

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    public abstract UserDto toDto(User user);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "refreshTokenList", ignore = true)
    public abstract User toEntity(UserDto userDto);

    @AfterMapping
    void afterMapping(UserDto source, @MappingTarget User target) {

        idMap(source, target);
        merge(target);
    }
}
