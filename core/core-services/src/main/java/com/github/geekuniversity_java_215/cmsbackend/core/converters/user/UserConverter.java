package com.github.geekuniversity_java_215.cmsbackend.core.converters.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
public class UserConverter extends AbstractConverter<User, UserDto, UserSpecDto> {

    @Autowired
    public UserConverter(UserMapper userMapper) {
        this.entityMapper = userMapper;

        this.entityClass = User.class;
        this.dtoClass = UserDto.class;
        this.specClass = UserSpecDto.class;
    }


    @Override
    protected void validate(User user) {
        super.validate(user);



        // ... custom validation
    }
}
