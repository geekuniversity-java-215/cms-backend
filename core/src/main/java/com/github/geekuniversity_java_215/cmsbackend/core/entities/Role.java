package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="rolez")
public class Role extends AbstractEntity {

    //public static final String RESOURCE  = "ROLE_RESOURCE";
    //public static final String REFRESH   = "ROLE_REFRESH";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String USER      = "ROLE_USER";
    public static final String ADMIN     = "ROLE_ADMIN";

    @Column(unique=true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Person> users = new HashSet<>();


    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> gePerson() {
        return users;
    }
}
