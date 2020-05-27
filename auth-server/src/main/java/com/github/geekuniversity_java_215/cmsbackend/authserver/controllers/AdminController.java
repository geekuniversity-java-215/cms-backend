package com.github.geekuniversity_java_215.cmsbackend.authserver.controllers;

import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.AuthType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.aop.ValidAuthenticationType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.TokenService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
	public ResponseEntity<String> test() {

        System.out.println(userService.getCurrentUser());
        System.out.println(UserService.getCurrentUsername());

        return  ResponseEntity.ok("hello world");
	}


    @PostMapping("/user/revoke_token")
    @ValidAuthenticationType({AuthType.BASIC_AUTH, AuthType.ACCESS_TOKEN})
    @Secured(UserRole.ADMIN)
    public ResponseEntity<String> revokeTokenByUsername(@RequestBody String username) {

        HttpStatus result;
        User user = userService.findByUsername(username).orElse(null);
        
        if (user == null) {
            result = HttpStatus.NOT_FOUND;
        }
        else {
            tokenService.revokeRefreshToken(user.getRefreshTokenList());
            result = HttpStatus.OK;
        }
        return new ResponseEntity<>(result);
    }

}
