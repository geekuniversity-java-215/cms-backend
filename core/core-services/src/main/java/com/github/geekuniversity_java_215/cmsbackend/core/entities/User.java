package com.github.geekuniversity_java_215.cmsbackend.core.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import lombok.*;


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
        name = "uzer",
        indexes = {@Index(name = "user_account_id_idx", columnList = "account_id"),
                @Index(name = "user_first_name_last_name_unq", columnList = "last_name, first_name",unique = true)
        })
@Data
@EqualsAndHashCode(callSuper=true)
public class User extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_id_seq")
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @Column(name = "login")
    private String login;     // use email as login ??

    @NotNull
    @Column(name = "password") // bcrypt hash
    private String password;

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Set<UserRole> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Column(name = "role_id")
    private Set<UserRole> roles = new HashSet<>();

    // Это список только refresh_token
    @NotNull
    @OneToMany(mappedBy= "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<RefreshToken> refreshTokenList = new ArrayList<>();

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;     // use email as login ??

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    @OneToOne(mappedBy= "user", orphanRemoval = true)
    @OrderBy("id ASC")
    private Client client;

    @OneToOne(mappedBy= "user", orphanRemoval = true)
    @OrderBy("id ASC")
    private Courier courier;




    protected User() {}

    public User(@NotNull String login,
                @NotNull String password,
                @NotNull String firstName,
                @NotNull String lastName,
                @NotNull String email,
                @NotNull String phoneNumber) {

        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
        account.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", account=" + account +
                '}';
    }

    @JsonIgnore
    public String getFullName() {
        return lastName + firstName;
    }
}
