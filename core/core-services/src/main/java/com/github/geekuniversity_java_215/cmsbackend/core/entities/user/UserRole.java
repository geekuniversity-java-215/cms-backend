package com.github.geekuniversity_java_215.cmsbackend.core.entities.user;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name="uzer_role")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRole extends AbstractEntity {

    public static final String ADMIN                  = "ROLE_ADMIN";
    public static final String USER                   = "ROLE_USER";
    public static final String ANONYMOUS              = "ROLE_ANONYMOUS";
    public static final String RESOURCE               = "ROLE_RESOURCE";
    public static final String REFRESH                = "ROLE_REFRESH";
    //public static final String REGISTRAR              = "ROLE_REGISTRAR";
    public static final String CONFIRM_REGISTRATION   = "ROLE_CONFIRM_REGISTRATION";

    public final static List<String> ROLE_NAMES = new ArrayList<>();

    static {
        ROLE_NAMES.add(ADMIN);
        ROLE_NAMES.add(USER);
        ROLE_NAMES.add(ANONYMOUS);
        ROLE_NAMES.add(RESOURCE);
        ROLE_NAMES.add(REFRESH);
        //ROLE_NAMES.add(REGISTRAR);
        ROLE_NAMES.add(CONFIRM_REGISTRATION);
    }

    @Column(unique=true)
    private String name;

//    @Getter
//    @ManyToMany(mappedBy = "roles")
//    private final Set<User> userList = new HashSet<>();

//    @Getter
//    @ManyToMany(mappedBy = "roles")
//    private final Set<User> userList = new HashSet<>();


    public UserRole(String name) {
        this.name = name;
    }

    public static Set<GrantedAuthority> rolesToGrantedAuthority(Set<UserRole> roles) {
        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
















    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return name.equals(userRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public enum Values {
        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER"),
        ANONYMOUS("ROLE_ANONYMOUS"),
        RESOURCE("ROLE_RESOURCE"),
        REFRESH("ROLE_REFRESH");

        private final static Map<String, Values> ENUM_MAP = new HashMap<>();

        static {
            for(Values item : Values.values()) {
                if (ENUM_MAP.containsKey(item.name)){
                    throw new RuntimeException("UserRole.Values duplicate code");
                }
                ENUM_MAP.put(item.name, item);
            }
        }

        @NotNull
        public static Values nameOf(String name) throws IllegalArgumentException {

            Values result = ENUM_MAP.get(name);
            if (result == null) {
                throw new IllegalArgumentException("Values - no matching constant for [" + name + "]");
            }
            return result;
        }

        @Getter
        private String name;

        Values(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    */


