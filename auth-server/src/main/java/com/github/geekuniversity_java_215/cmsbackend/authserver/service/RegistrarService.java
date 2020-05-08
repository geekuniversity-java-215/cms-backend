package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.exceptions.UserAlreadyExistsException;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.TokenType;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.geekuniversity_java_215.cmsbackend.core.configurations.CoreSpringConfiguration.ISSUER;

@Service
@Slf4j
public class RegistrarService {

    private final UserService userService;
    private final UserRoleService userRoleService;

    private final UnconfirmedUserService unconfirmedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final MailService mailService;
    private final Validator validator;

    public RegistrarService(UserService userService,
                            UserRoleService userRoleService, UnconfirmedUserService unconfirmedUserService,
                            PasswordEncoder passwordEncoder,
                            JwtTokenService jwtTokenService,
                            MailService mailService, Validator validator) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.unconfirmedUserService = unconfirmedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.mailService = mailService;
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
        newUser.getRoles().add(userRoleService.findByName(UserRole.CONFIRM_REGISTRATION));

        // save new user to UnconfirmedUser
        unconfirmedUserService.save(newUser);

        // create confirmation JWT
        Set<String> confirmationRole = newUser.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet());
        String registrantToken = jwtTokenService.createJWT(
            TokenType.CONFIRM,
            newUser.getUsername(),
            ISSUER,
            newUser.getUsername(),
            confirmationRole);

        mailService.generateConfirmationUrl()




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



//Set<String> confirmationRoles = new HashSet<>(Collections.singletonList(UserRole.CONFIRM_REGISTRATION));
