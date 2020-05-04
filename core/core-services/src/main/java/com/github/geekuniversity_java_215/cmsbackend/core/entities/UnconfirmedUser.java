package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntityNoGen;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@MappedSuperclass
@Entity
//@AttributeOverride(name="id", column = )
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@Table(
    name = "unconfirmed_uzer",
    indexes = {@Index(name = "unconfirmed_uzer_first_name_last_name_unq",
            columnList = "last_name, first_name",unique = true)
    })
@Data
@EqualsAndHashCode(callSuper=true)
public class UnconfirmedUser extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_id_seq")
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @Column(name = "login")
    private String login;

    @NotNull
    @Column(name = "password") // bcrypt hash
    private String password;

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Column(name = "role_id")
    private Set<UserRole> roles = new HashSet<>();

    // registration confirmation JWT
    @NotNull
    private String token;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    protected UnconfirmedUser() {}

    public UnconfirmedUser(@NotNull String firstName,
                           @NotNull String lastName,
                           @NotNull String email,
                           @NotNull String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }

    @JsonIgnore
    public String getFullName() {
        return lastName + firstName;
    }
}
