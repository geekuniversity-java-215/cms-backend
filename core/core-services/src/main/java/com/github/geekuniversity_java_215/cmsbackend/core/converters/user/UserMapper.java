package com.github.geekuniversity_java_215.cmsbackend.core.converters.user;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.client.ClientMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.courier.CourierMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.userrole.UserRoleMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserRoleDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class UserMapper extends AbstractMapper<User, UserDto> {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;


    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = userService;
        constructor = new EntityConstructor();
    }

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    public abstract UserDto toDto(User user);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "refreshTokenList", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract User toEntity(UserDto userDto);

    @AfterMapping
    User afterMapping(UserDto source, @MappingTarget User target) {

        target = merge(source, target);

        // update roles
        for (UserRoleDto role : source.getRoles()) {
            target.getRoles().add(userRoleService.findByName(role.getName())
                .orElseThrow(() -> new IllegalArgumentException("UserRole " + role.getName() + "not found")));
        }
        return target;
    }

    protected class EntityConstructor extends Constructor<User, UserDto> {

        //private UserRoleService userRoleService;

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
