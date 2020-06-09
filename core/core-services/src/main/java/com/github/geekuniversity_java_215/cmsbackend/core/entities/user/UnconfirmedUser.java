package com.github.geekuniversity_java_215.cmsbackend.core.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.oauth_utils.data.TokenType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    //public static final Duration TTL = Duration.ofDays(1);

    @NotNull
    @Column(name = "user_name")
    private String username;  // username is approved by dictionary.com //  use email as username ???

    @NotNull
    @Column(name = "password") // bcrypt hash
    private String password;

    @ManyToMany(/*cascade = CascadeType.MERGE,*/ fetch = FetchType.EAGER)
    @Column(name = "role_id")
    @JoinTable(name = "unconfirmed_uzer_roles")
    private Set<UserRole> roles = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Set<UserRole> roles = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @Column(name = "role_id")
//    private Set<UserRole> roles = new HashSet<>();

//    // registration confirmation JWT
//    @NotNull
//    private String token;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;      // use email as username ??

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "expired_at", updatable = false)
    @Getter
    protected Instant expiredAt;

    protected UnconfirmedUser() {
        expiredAt = Instant.now().plus(Duration.ofSeconds(TokenType.CONFIRM.getTtl()));
    }


    public UnconfirmedUser(@NotNull String username,
                           @NotNull String password,
                           @NotNull String firstName,
                           @NotNull String lastName,
                           @NotNull String email,
                           @NotNull String phoneNumber) {
        super();

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public User toUser() {

        User result = new User(
                username,
                password,
                firstName,
                lastName,
                email,
                phoneNumber);

        // clone set of roles, roles stay same
        //Set<UserRole> userRoles = new HashSet<>(getRoles());
        //result.setRoles(userRoles);

        return result;
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
