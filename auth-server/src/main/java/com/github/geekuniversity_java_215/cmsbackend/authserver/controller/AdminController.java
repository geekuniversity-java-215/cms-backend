package com.github.geekuniversity_java_215.cmsbackend.authserver.controller;

import com.github.geekuniversity_java_215.cmsbackend.authserver.config.AuthType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.config.aspect.ValidAuthenticationType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.TokenService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public AdminController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/test")
    @ValidAuthenticationType({AuthType.BASIC_AUTH, AuthType.ACCESS_TOKEN})
    @Secured(UserRole.ADMIN)
	public ResponseEntity<String> hello() {

        return  ResponseEntity.ok("Hello World");
	}


    @PostMapping("/user/revoke_token")
    @ValidAuthenticationType({AuthType.BASIC_AUTH, AuthType.ACCESS_TOKEN})
    @Secured(UserRole.ADMIN)
    public ResponseEntity<String> revokeToken(@RequestBody String login) {

        HttpStatus status;

        User user = userService.findByLogin(login);

        if (user == null) {
            status = HttpStatus.NOT_FOUND;
        }
        else {
           tokenService.deleteByUser(user);
            status = HttpStatus.OK;
        }
        
        return new ResponseEntity<>(status);
    }







}
