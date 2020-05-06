package com.github.geekuniversity_java_215.cmsbackend.authserver.controllers;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.JwtTokenService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.UnconfirmedUserService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.HttpResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.TokenType;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.github.geekuniversity_java_215.cmsbackend.core.configurations.CoreSpringConfiguration.ISSUER;

@RestController
@RequestMapping("/registration/")
@Slf4j
public class RegistrarController {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final Validator validator;

    public RegistrarController(UserService userService, UnconfirmedUserService unconfirmedUserService,
                               PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService,
                               Validator validator) {
        this.userService = userService;
        this.unconfirmedUserService = unconfirmedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.validator = validator;
    }


    @PostMapping("/new")
    @Secured({UserRole.REGISTRAR})
    public ResponseEntity<String> add(@RequestBody UnconfirmedUser newUser) {

        ResponseEntity<String> result; // ResponseEntity.badRequest().body("Bad user");

        try {

            Set<ConstraintViolation<UnconfirmedUser>> violations = validator.validate(newUser);
            if (violations.size() != 0) {
                return ResponseEntity.badRequest().body("User validation failed:" +violations);
            }

            // user already exists in User or in UnconfirmedUser
            if (userService.checkIfExists(newUser.toUser()) ||
                unconfirmedUserService.checkIfExists(newUser)) {

                return ResponseEntity.badRequest().body("User already exists");
            }

            log.info("Adding new user: {}", newUser);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

            Set<String> userRoles = new HashSet<>(Collections.singletonList(UserRole.CONFIRM_REGISTRATION));

            String registrantToken = jwtTokenService.createJWT(
                TokenType.REFRESH,
                newUser.getLogin(),
                ISSUER,
                newUser.getLogin(),
                userRoles,
                TokenType.CONFIRM.getTtl());

            unconfirmedUserService.save(newUser);

            //ToDo: send email to user to complete registration

            // toDo: remove returning registrantToken after DEBUG
            result = ResponseEntity.ok(registrantToken);
        }
        catch (Exception e) {
            log.error("Adding new user error", e);
            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error: " + e.getMessage());
        }
        return result;
    }


    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {

        ResponseEntity<String> result; //= ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {

            Claims claims = jwtTokenService.decodeJWT(token);
            String login = claims.getSubject();
            result = ResponseEntity.ok("Registration successful");
            // JwtException

            // ToDo: move UnconfirmedUser to User

            // ToDo: redirect user to cms app front page
        }
        catch (Exception e) {
            log.error("Adding new user error", e);
            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error: " + e.getMessage());
        }
        return result;
    }


}
