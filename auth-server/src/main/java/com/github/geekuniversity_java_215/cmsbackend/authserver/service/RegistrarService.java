package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.exceptions.UserAlreadyExistsException;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.TokenType;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.github.geekuniversity_java_215.cmsbackend.core.configurations.CoreSpringConfiguration.ISSUER;

@Service
@Slf4j
public class RegistrarService {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final Validator validator;

    public RegistrarService(UserService userService,
                            UnconfirmedUserService unconfirmedUserService,
                            PasswordEncoder passwordEncoder,
                            JwtTokenService jwtTokenService,
                            Validator validator) {
        this.userService = userService;
        this.unconfirmedUserService = unconfirmedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.validator = validator;
    }

    public String add(UnconfirmedUser newUser) {

        Set<ConstraintViolation<UnconfirmedUser>> violations = validator.validate(newUser);
        if (violations.size() != 0) {
            throw new ConstraintViolationException("User validation failed", violations);
        }

        // user already exists in User or in UnconfirmedUser
        if (userService.checkIfExists(newUser.toUser()) ||
            unconfirmedUserService.checkIfExists(newUser)) {
            throw new UserAlreadyExistsException(newUser.getUsername());
        }

        log.info("Adding new user: {}", newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Set<String> userRoles = new HashSet<>(Collections.singletonList(UserRole.CONFIRM_REGISTRATION));

        String registrantToken = jwtTokenService.createJWT(
            TokenType.REFRESH,
            newUser.getUsername(),
            ISSUER,
            newUser.getUsername(),
            userRoles,
            TokenType.CONFIRM.getTtl());

        unconfirmedUserService.save(newUser);

        // ToDo: send email to user to complete registration

        return registrantToken;
    }

    public void confirm(String token) {

        Claims claims = jwtTokenService.decodeJWT(token);
        String username = claims.getSubject();

        // JwtException

        // ToDo: move UnconfirmedUser to User

        // ToDo: redirect user to cms app front page

    }




}
