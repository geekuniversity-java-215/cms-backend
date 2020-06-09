package com.github.geekuniversity_java_215.cmsbackend.core.converters.user;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.account.AccountMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.userrole.UserRoleMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserRoleDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class, UserRoleMapper.class, AccountMapper.class})
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

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    public abstract UserDto toDto(User user);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "refreshTokenList", expression = "java(null)") // всегда подгружаем из БД
    @Mapping(target = "account", ignore = true)
    public abstract User toEntity(UserDto userDto);


//    @AfterMapping
//    UserDto afterMapping(User source, @MappingTarget UserDto target) {
//
//        UserDto result = target;
//        result.getAccount().setUser(result);
//        return result;
//    }


    @AfterMapping
    User afterMapping(UserDto source, @MappingTarget User target) {

        return merge(source, target);

//        // update roles manually
//        target.getRoles().clear();
//        for (UserRoleDto role : source.getRoles()) {
//            target.getRoles().add(userRoleService.findByName(role.getName())
//                .orElseThrow(() -> new IllegalArgumentException("UserRole " + role.getName() + "not found")));
//        }
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
