package com.github.geekuniversity_java_215.cmsbackend.authserver.controllers;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.exceptions.UserAlreadyExistsException;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.RegistrarService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;

@RestController
@RequestMapping("/registration/")
@Slf4j
public class RegistrarController {

    private final RegistrarService registrarService;

    public RegistrarController(RegistrarService registrarService) {
        this.registrarService = registrarService;
    }


    @PostMapping("/new")
    @Secured({UserRole.REGISTRAR})
    public ResponseEntity<?> add(@RequestBody UnconfirmedUser newUser) {

        ResponseEntity<?> result; // ResponseEntity.badRequest().body("Bad user");

        try {
            String registrantToken = registrarService.add(newUser);
            // toDo: remove returning registrantToken after DEBUG
            result = ResponseEntity.ok(registrantToken);
        }
        catch (ConstraintViolationException e) {
            log.error("User validation error", e);
            String message = "User validation error: " + e.getConstraintViolations().toString();
            result = ResponseEntity.badRequest().body(message);
        }
        catch(UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            log.error("Adding new user error", e);
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {

        ResponseEntity<?> result; //= ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            registrarService.confirm(token);

            // отправляем пользователя на login page фронта
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("https://natribu.org/ru/"));
            result = new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        catch (JwtException e) {
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (Exception e) {
            log.error("Confirm new user error", e);
            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + ": " +  e.getMessage());
        }
        return result;
    }


}
