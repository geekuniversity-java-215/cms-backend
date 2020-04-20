package com.github.geekuniversity_java_215.cmsbackend.entites.base;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.entites.Order;


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
public abstract class Person extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "person_id_seq")
    protected Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;


    @NotNull
    @OneToMany(mappedBy= "courier", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Order> orderList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Account getAccount() {
        return account;
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
