package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Счет
 */
@Entity
@Table(name = "account")
public class Account extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "account_id_seq")
    protected Long id;

    @Column(name = "balance")
    private BigDecimal balance = new BigDecimal(0);

    @OneToOne(mappedBy = "account")
    private Person person;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Account{" +
               "balance=" + balance +
               '}';
    }
}
