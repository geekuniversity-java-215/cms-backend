package com.github.geekuniversity_java_215.cmsbackend.protocol.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UnconfirmedUserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


    public UnconfirmedUserDto(@NotNull String username,
                              @NotNull String password,
                              @NotNull String firstName,
                              @NotNull String lastName,
                              @NotNull String email,
                              @NotNull String phoneNumber) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
