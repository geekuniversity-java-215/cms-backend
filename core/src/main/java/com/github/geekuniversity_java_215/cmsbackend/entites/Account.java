package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends AbstractEntity {

    @Column(name = "num")
    private long num;

    @OneToOne(mappedBy = "account")
    private Person person;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
