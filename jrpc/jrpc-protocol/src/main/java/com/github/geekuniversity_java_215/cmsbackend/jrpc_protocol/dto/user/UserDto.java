package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.Account.AccountDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {

    private String username;
    private String password;
    private Set<UserRoleDto> roles = new HashSet<>();

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;

    private String payPalEmail;


    private AccountDto account;
    private ClientDto client;
    private CourierDto courier;

    //private Account account;   // Not implemented
    // private Client client;   // Not implemented
    // private Courier courier;  // Not implemented

    @Override
    public String toString() {
        return "UserDto{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
    }
}
