package com.github.geekuniversity_java_215.cmsbackend.authserver.controller;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UnconfirmedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration/")
@Slf4j
public class RegistrarController {

    private final UnconfirmedUserService unconfirmedUserService;

    public RegistrarController(UnconfirmedUserService unconfirmedUserService) {
        this.unconfirmedUserService = unconfirmedUserService;
    }


    @PostMapping("/new")
    @Secured({UserRole.REGISTRAR})
    public ResponseEntity<String> add(@RequestBody UnconfirmedUser user) {

        ResponseEntity<String> result = ResponseEntity.badRequest().body("...");

        try {
            log.info("Adding new user: {}", user);
            unconfirmedUserService.save(user);
            result = ResponseEntity.ok("Adding new user: " + user);
        }
        catch (Exception e) {
            log.error("Adding new user error", e);
        }
        return result;
    }


    @PostMapping("/confirm")
    public ResponseEntity<String> confirm() {
        return  ResponseEntity.ok("Registration successful");
    }


}
