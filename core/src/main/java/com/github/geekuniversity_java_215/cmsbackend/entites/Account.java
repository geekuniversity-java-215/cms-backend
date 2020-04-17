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


    @Column(name = "num")
    private long num;

    @OneToOne(mappedBy = "account")
    private Person person;


    
    protected Account() {}

    public Account(long num) {
        this.num = num;
    }



    public long getNum() {
        return num;
    }

    protected void setNum(long num) {
        this.num = num;
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
               "num=" + num +
               '}';
    }
}
