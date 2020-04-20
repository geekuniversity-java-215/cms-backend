package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;


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
    private long balance;

    @OneToOne(mappedBy = "account")
    private Person person;

    public Account() {
        this.balance = 0L;
    }

    public long getbalance() {
        return balance;
    }

    public void setbalance(long num) {
        this.balance = num;
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
