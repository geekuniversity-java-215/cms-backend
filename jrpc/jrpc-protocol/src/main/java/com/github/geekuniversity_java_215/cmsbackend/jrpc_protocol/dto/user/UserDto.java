package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Set<UserRoleDto> roles = new HashSet<>();

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;
    //private Account account;   // Not implemented
    // private Client client;   // Not implemented
    // private Courier courier;  // Not implemented
}
