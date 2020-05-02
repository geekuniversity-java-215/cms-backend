package com.github.geekuniversity_java_215.cmsbackend.core.entities.base;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@Table(
    name = "person",
    indexes = {@Index(name = "_idx", columnList = "account_id"),
               @Index(name = "_unq", columnList = "last_name, first_name",unique = true)
    })
@Data
@EqualsAndHashCode(callSuper=true)
public abstract class Person extends AbstractEntityNoGen {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "person_id_seq")
    @EqualsAndHashCode.Exclude
    protected Long id;

    @NotNull
    @Column(name = "login")
    protected String login;

    @NotNull
    @Column(name = "password") // bcrypt hash
    protected String password;


    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<UserRole> roles = new HashSet<>();

    // Это список только refresh_token
    @NotNull
    @OneToMany(mappedBy= "person", orphanRemoval = true, cascade = CascadeType.ALL)
    //@OrderBy("id ASC")
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @NotNull
    @Column(name = "first_name")
    protected String firstName;

    @NotNull
    @Column(name = "last_name")
    protected String lastName;

    @NotNull
    @Column(name = "email")
    protected String email;

    @NotNull
    @Column(name = "phone_number")
    protected String phoneNumber;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    protected Account account;


    @NotNull
    @OneToMany(mappedBy= "courier", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    protected List<Order> orderList = new ArrayList<>();


    public Person() {}

    public Person(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String phoneNumber) {
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
        account.setPerson(this);
    }

    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", account=" + account +
               '}';
    }
}
