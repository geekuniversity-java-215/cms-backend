package com.github.geekuniversity_java_215.cmsbackend.core.converters.userrole;


import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserRoleDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class UserRoleMapper extends AbstractMapper<UserRole, UserRoleDto> {

    @Autowired
    private UserRoleService userRoleService;

    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = userRoleService;
        constructor = new EntityConstructor();
    }



    public abstract UserRoleDto toDto(UserRole user);

    @Mapping(target = "name", ignore = true)
    public abstract UserRole toEntity(UserRoleDto userDto);

    @AfterMapping
    UserRole afterMapping(UserRoleDto source, @MappingTarget UserRole target) {
        return merge(source, target);
    }

    protected class EntityConstructor extends Constructor<UserRole, UserRoleDto> {
        @Override
        public UserRole create(UserRoleDto dto, UserRole entity) {
            return new UserRole(dto.getName());
        }
    }





}
