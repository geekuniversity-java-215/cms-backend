package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntityNoId;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account extends AbstractEntityNoId {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "account_id_seq")
    protected Long id;


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
