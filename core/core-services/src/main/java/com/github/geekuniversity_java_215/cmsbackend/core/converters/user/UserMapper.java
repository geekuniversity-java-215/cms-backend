package com.github.geekuniversity_java_215.cmsbackend.core.converters.user;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.account.AccountMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(config = AbstractMapper.class,
        uses = {AccountMapper.class})
public abstract class UserMapper extends AbstractMapper<User, UserDto> {

    @Autowired
    private UserService userService;

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
