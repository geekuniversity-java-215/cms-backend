package com.github.geekuniversity_java_215.cmsbackend.authserver.controller;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.JwtTokenService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.UnconfirmedUserService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.protocol.token.TokenType;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.github.geekuniversity_java_215.cmsbackend.core.configuration.CoreSpringConfiguration.ISSUER;

@RestController
@RequestMapping("/registration/")
@Slf4j
public class RegistrarController {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public RegistrarController(UserService userService, UnconfirmedUserService unconfirmedUserService,
                               PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.unconfirmedUserService = unconfirmedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }


    @PostMapping("/new")
    @Secured({UserRole.REGISTRAR})
    public ResponseEntity<String> add(@RequestBody UnconfirmedUser user) {

        ResponseEntity<String> result = ResponseEntity.badRequest().body("Bad user");

        //ToDo: validate user

        try {
            // user already exists in User
            // check email too ?
            if (userService.findByLogin(user.getLogin()).isPresent()) {
                result = ResponseEntity.badRequest().body("User already exists");
                return result;
            }


            // user already exists in UnconfirmedUser
            // check email too ?
            if (unconfirmedUserService.findByLogin(user.getLogin()).isPresent()) {
                result = ResponseEntity.badRequest().body("User already exists");
                return result;
            }


            log.info("Adding new user: {}", user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Set<String> userRoles = new HashSet<>(Collections.singletonList(UserRole.CONFIRM_REGISTRATION));

            String registrantToken = jwtTokenService.createJWT(
                    TokenType.REFRESH,
                    user.getLogin(),
                    ISSUER,
                    user.getLogin(),
                    userRoles,
                    TokenType.CONFIRM.getTtl());

            unconfirmedUserService.save(user);

            //ToDo: send email to user to complete registration

            // toDo: remove returning registrantToken after DEBUG
            result = ResponseEntity.ok(registrantToken);
        }
        catch (Exception e) {
            log.error("Adding new user error", e);
        }
        return result;
    }


    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {

        ResponseEntity<String> result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {

            Claims claims = jwtTokenService.decodeJWT(token);
            String login = claims.getSubject();
            result = ResponseEntity.ok("Registration successful");
            // JwtException

            // ToDo: move UnconfirmedUser to User

            // ToDo: redirect user to cms app front page
        }
        catch (Exception e) {
            log.error("confirm error", e);
        }


        return result;
    }


}
