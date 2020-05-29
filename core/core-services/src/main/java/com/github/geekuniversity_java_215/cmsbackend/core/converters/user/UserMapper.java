package com.github.geekuniversity_java_215.cmsbackend.core.converters.user;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;

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
        constructor = new UserConstructor();
    }

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    public abstract UserDto toDto(User user);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "refreshTokenList", ignore = true)
    public abstract User toEntity(UserDto userDto);

    @AfterMapping
    User afterMapping(UserDto source, @MappingTarget User target) {
        return merge(source, target);
    }

    public static class UserConstructor extends Constructor<User, UserDto> {
        @Override
        public User create(UserDto dto, User entity) {

        // Mapstruct 1.4 maybe will support constructors with params
        return new User(
            dto.getUsername(),
            dto.getPassword(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getEmail(),
            dto.getPhoneNumber());
        }
    }
}
