package com.github.geekuniversity_java_215.cmsbackend.entites.base;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(
    indexes = {@Index(name = "_idx", columnList = "account_id"),
               @Index(name = "_unq", columnList = "last_name, first_name",unique = true)
    })

@Data
@EqualsAndHashCode(callSuper=true)
@RequiredArgsConstructor
public abstract class Person extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "person_id_seq")
    protected Long id;

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


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    @NotNull
    @OneToMany(mappedBy= "courier", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Order> orderList = new ArrayList<>();

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
