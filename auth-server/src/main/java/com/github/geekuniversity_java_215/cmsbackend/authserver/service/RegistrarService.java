package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.properties.AuthServerConfig;
import com.github.geekuniversity_java_215.cmsbackend.core.configurations.CoreSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.exceptions.UserAlreadyExistsException;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import com.github.geekuniversity_java_215.cmsbackend.oauth_utils.data.TokenType;
import com.github.geekuniversity_java_215.cmsbackend.oauth_utils.services.JwtTokenService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Service
@Transactional
@Slf4j
public class RegistrarService {

    private final CoreSpringConfiguration coreSpringConfiguration;
    private final AuthServerConfig authServerConfig;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final MailService mailService;
    private final Validator validator;

    public RegistrarService(CoreSpringConfiguration coreSpringConfiguration, AuthServerConfig authServerConfig, UserService userService,
                            UserRoleService userRoleService, UnconfirmedUserService unconfirmedUserService,
                            PasswordEncoder passwordEncoder,
                            JwtTokenService jwtTokenService,
                            MailService mailService, Validator validator) {
        this.coreSpringConfiguration = coreSpringConfiguration;
        this.authServerConfig = authServerConfig;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.unconfirmedUserService = unconfirmedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.mailService = mailService;
        this.validator = validator;
    }

    public String registrate(UnconfirmedUser newUser) {

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
            coreSpringConfiguration.getISSUER(),
            newUser.getUsername(),
            confirmationRole);

        URI url = unchecked(() -> new URI(authServerConfig.getConfirmationUrl() + "?token=" + registrantToken)).get();

        mailService.sendRegistrationConfirmation(newUser, url);

        return registrantToken;
    }


    public void confirm(String token) {

        // will throw exception if token not valid
        Claims claims = jwtTokenService.decodeJWT(token);

        String username = claims.getSubject();
        UnconfirmedUser unconfirmedUser = unconfirmedUserService.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        User user = unconfirmedUser.toUser();


        // Set user roles to USER
        user.getRoles().clear();
        user.getRoles().add(userRoleService.findByName(UserRole.USER));
        // save user
        userService.save(user);

        // remove unconfirmedUser
        unconfirmedUserService.delete(unconfirmedUser);
    }
}



//Set<String> confirmationRoles = new HashSet<>(Collections.singletonList(UserRole.CONFIRM_REGISTRATION));
