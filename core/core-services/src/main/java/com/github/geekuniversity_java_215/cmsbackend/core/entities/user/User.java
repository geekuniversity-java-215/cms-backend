package com.github.geekuniversity_java_215.cmsbackend.core.entities.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.oauth2.token.RefreshToken;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

//@MappedSuperclass
@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(
        name = "uzer",
        indexes = {@Index(name = "user_account_id_idx", columnList = "account_id"),
                @Index(name = "user_first_name_last_name_unq", columnList = "last_name, first_name",unique = true)
        })

@NamedEntityGraph(name = User.FULL_ENTITY_GRAPH,
    attributeNodes = @NamedAttributeNode("refreshTokenList")
)

@EntityListeners(UserToPersistListener.class)
@Data
public class User extends AbstractEntity {

    public static final String FULL_ENTITY_GRAPH = "User.full";

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(generator = "user_id_seq")
//    @EqualsAndHashCode.Exclude
//    private Long id;

    @NotNull
    @Column(name = "user_name")
    @Setter(AccessLevel.NONE)
    private String username;     // username is approved by dictionary.com //  use email as username ???

    @NotNull
    @Column(name = "password") // bcrypt hash
    private String password;

//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Set<UserRole> roles = new HashSet<>();

    @NotNull
    @NotEmpty
    @ManyToMany(/*cascade = {CascadeType.MERGE},*/ fetch = FetchType.EAGER)
    @Column(name = "role_id")
    private Set<UserRole> roles = new HashSet<>();

    // Это список только refresh_token
    @NotNull
    @OneToMany(mappedBy= "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<RefreshToken> refreshTokenList = new ArrayList<>();

    @NotNull
    @Column(name = "first_name")
    @Setter(AccessLevel.NONE)
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    @Setter(AccessLevel.NONE)
    private String lastName;

    @NotNull
    @Column(name = "email")
    @Setter(AccessLevel.NONE)
    private String email;     // use email as username ??

    @Column(name = "paypal_email")
    private String payPalEmail;     // Paypal withdrawal mailbox

    @NotNull
    @Column(name = "phone_number")  // may change or not?
    @Setter(AccessLevel.NONE)
    private String phoneNumber;

    @OneToOne(/*cascade = CascadeType.ALL,*/ orphanRemoval = true/*, fetch = FetchType.EAGER*/)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    @OneToOne(mappedBy= "user", orphanRemoval = true)
    @OrderBy("id ASC")
    private Client client;

    @OneToOne(mappedBy= "user", orphanRemoval = true)
    @OrderBy("id ASC")
    private Courier courier;

    public User() {}

    public User(@NotNull String username,
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

        this.getRoles().add(UserRole.getByName(UserRole.USER));
    }

//    protected void setId(Long id) {
//        this.id = id;
//    }

    public void setAccount(Account account) {

        if (account != null) {
            this.account = account;
            account.setUser(this);
        }
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

    public void setClient(Client client) {
        this.client = client;
        if (client.getUser() != this) {
            client.setUser(this);
        }
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
        if (courier.getUser() != this) {
            courier.setUser(this);
        }
    }



    @PrePersist
    private void prePersists() {
    }

    // ToDo: что делать с остальными полями я хз
    //  пользователь идентифицируется(principals) по username,email, phoneNumber
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
            email.equals(user.email) &&
            phoneNumber.equals(user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, phoneNumber);
    }
}
