package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_role")
public class UserRole extends AbstractEntity {


    public static final String ADMIN     = "ROLE_ADMIN";
    public static final String USER      = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String RESOURCE  = "ROLE_RESOURCE";
    public static final String REFRESH   = "ROLE_REFRESH";


    @Getter
    @Setter
    @Column(unique=true)
    private String name;

    @Getter
    @ManyToMany(mappedBy = "roles")
    private Set<Person> personList = new HashSet<>();


    public UserRole() {}
    public UserRole(String name) {
        this.name = name;
    }
}
